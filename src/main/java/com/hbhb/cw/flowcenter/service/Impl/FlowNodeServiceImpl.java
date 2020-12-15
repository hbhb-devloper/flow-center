package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.mapper.FlowLineMapper;
import com.hbhb.cw.flowcenter.mapper.FlowNodeMapper;
import com.hbhb.cw.flowcenter.mapper.FlowNodePropMapper;
import com.hbhb.cw.flowcenter.model.FlowLine;
import com.hbhb.cw.flowcenter.model.FlowNode;
import com.hbhb.cw.flowcenter.model.FlowNodeProp;
import com.hbhb.cw.flowcenter.service.FlowNodeService;
import com.hbhb.cw.flowcenter.web.vo.FlowNodeVO;

import org.beetl.sql.core.page.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangxiaogang
 */
@Service
@Slf4j
@SuppressWarnings(value = {"unchecked"})
public class FlowNodeServiceImpl implements FlowNodeService {

    @Resource
    private FlowNodeMapper flowNodeMapper;
    @Resource
    private FlowLineMapper flowLineMapper;
    @Resource
    private FlowNodePropMapper flowNodePropMapper;

    @Override
    public PageResult<FlowNodeVO> pageFlowNode(Integer pageNum, Integer pageSize) {
        return flowNodeMapper.createLambdaQuery().page(pageNum, pageSize, FlowNodeVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFlowNode(String id) {
        // 先删除节点属性
        flowNodePropMapper.createLambdaQuery().andEq(FlowNodeProp::getFlowNodeId, id).delete();
        // 再删除节点
        flowNodeMapper.deleteById(id);
    }

    @Override
    public FlowLine getNodeLine(String fromNodeId, String toNodeId) {
        return flowLineMapper.createLambdaQuery()
                .andEq(FlowLine::getFromNodeId, fromNodeId)
                .andEq(FlowLine::getToNodeId, toNodeId)
                .single();
    }

    @Override
    public void deleteNodeLine(String id) {
        flowLineMapper.deleteById(id);
    }

    @Override
    public List<SelectVO> getNodeMap(Long flowId) {
        List<FlowNode> list = flowNodeMapper.createLambdaQuery()
                .andEq(FlowNode::getFlowId, flowId)
                .select(FlowNode::getId, FlowNode::getNodeName);
        return Optional.ofNullable(list)
                .orElse(new ArrayList<>())
                .stream()
                .map(flowNode -> SelectVO.builder()
                        .id(flowNode.getFlowId())
                        .label(flowNode.getNodeName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public long getNodeNum(Long flowId) {
        return flowNodeMapper.createLambdaQuery().andEq(FlowNode::getFlowId, flowId).count();
    }
}
