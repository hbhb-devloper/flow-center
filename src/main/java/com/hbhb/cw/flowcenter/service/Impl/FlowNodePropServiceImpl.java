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

import java.math.BigDecimal;
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
    public void addNodeProp(FlowNodePropVO vo, Integer userId) {
        UserInfo userInfo = userApi.getUserInfoById(userId);

        FlowNodeProp flowNodeProp = BeanConverter.convert(vo, FlowNodeProp.class);
        flowNodeProp.setCreateTime(new Date());
        flowNodeProp.setCreateBy(userInfo.getNickName());
        if (vo.getAmount() != null) {
            flowNodeProp.setAmount(new BigDecimal(vo.getAmount()));
        }
        flowNodePropMapper.insertTemplate(flowNodeProp);
    }

    @Override
    public void updateNodeProp(FlowNodePropVO vo) {
        FlowNodeProp flowNodeProp = new FlowNodeProp();
        BeanConverter.copyProp(vo, flowNodeProp);
        flowNodePropMapper.updateTemplateById(flowNodeProp);
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
