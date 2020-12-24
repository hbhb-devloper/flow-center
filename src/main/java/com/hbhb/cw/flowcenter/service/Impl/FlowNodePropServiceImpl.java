package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.core.bean.BeanConverter;
import com.hbhb.cw.flowcenter.mapper.FlowNodePropMapper;
import com.hbhb.cw.flowcenter.model.FlowNodeProp;
import com.hbhb.cw.flowcenter.service.FlowNodePropService;
import com.hbhb.cw.flowcenter.vo.FlowNodePropVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tpson
 */
@Service
@Slf4j
public class FlowNodePropServiceImpl implements FlowNodePropService {

    @Resource
    private FlowNodePropMapper flowNodePropMapper;

    @Override
    public FlowNodePropVO getNodePropInfo(String flowNodeId) {
        FlowNodeProp flowNodeProp = flowNodePropMapper.createLambdaQuery()
                .andEq(FlowNodeProp::getFlowNodeId, flowNodeId)
                .single();
        return BeanConverter.convert(flowNodeProp, FlowNodePropVO.class);
    }

    @Override
    public void upsertNodeProp(FlowNodePropVO vo) {
        FlowNodeProp flowNodeProp = BeanConverter.convert(vo, FlowNodeProp.class);
        flowNodePropMapper.upsert(flowNodeProp);
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
