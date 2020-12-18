package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.core.bean.BeanConverter;
import com.hbhb.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.mapper.FlowNodePropMapper;
import com.hbhb.cw.flowcenter.model.FlowNodeProp;
import com.hbhb.cw.flowcenter.rpc.UserApiExp;
import com.hbhb.cw.flowcenter.service.FlowNodePropService;
import com.hbhb.cw.flowcenter.vo.FlowNodePropVO;
import com.hbhb.cw.systemcenter.vo.UserInfo;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlowNodePropServiceImpl implements FlowNodePropService {

    @Resource
    private FlowNodePropMapper flowNodePropMapper;
    @Resource
    private UserApiExp userApi;

    @Override
    public FlowNodePropVO getNodePropInfo(String flowNodeId) {
        FlowNodeProp flowNodeProp = flowNodePropMapper.createLambdaQuery()
                .andEq(FlowNodeProp::getFlowNodeId, flowNodeId)
                .single();
        return BeanConverter.convert(flowNodeProp, FlowNodePropVO.class);
    }

    @Override
    public void upsertNodeProp(FlowNodePropVO vo, Integer userId) {
        Date now = new Date();
        UserInfo userInfo = userApi.getUserInfoById(userId);

        FlowNodeProp flowNodeProp = BeanConverter.convert(vo, FlowNodeProp.class);
        if (StringUtils.isEmpty(vo.getFlowNodeId())) {
            flowNodeProp.setCreateTime(now);
            flowNodeProp.setCreateBy(userInfo.getNickName());
        } else {
            flowNodeProp.setUpdateTime(now);
            flowNodeProp.setUpdateBy(userInfo.getNickName());
        }
        flowNodePropMapper.upsertByTemplate(flowNodeProp);
    }

    @Override
    public void deleteNodeProp(Long id) {
        flowNodePropMapper.deleteById(id);
    }

    @Override
    public List<SelectVO> getAssignerList(Long flowId) {
        return flowNodePropMapper.selectAssignerList(flowId);
    }

    @Override
    public List<FlowNodePropVO> getNodeProps(Long flowId) {
        return flowNodePropMapper.selectNodePropList(flowId);
    }
}
