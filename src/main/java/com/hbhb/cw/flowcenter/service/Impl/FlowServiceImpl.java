package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.core.bean.BeanConverter;
import com.hbhb.cw.flowcenter.enums.code.FlowErrorCode;
import com.hbhb.cw.flowcenter.exception.FlowException;
import com.hbhb.cw.flowcenter.mapper.FlowLineMapper;
import com.hbhb.cw.flowcenter.mapper.FlowMapper;
import com.hbhb.cw.flowcenter.mapper.FlowNodeMapper;
import com.hbhb.cw.flowcenter.mapper.FlowNodeNoticeMapper;
import com.hbhb.cw.flowcenter.mapper.FlowNodePropMapper;
import com.hbhb.cw.flowcenter.mapper.FlowUnitMapper;
import com.hbhb.cw.flowcenter.model.Flow;
import com.hbhb.cw.flowcenter.model.FlowLine;
import com.hbhb.cw.flowcenter.model.FlowNode;
import com.hbhb.cw.flowcenter.model.FlowNodeNotice;
import com.hbhb.cw.flowcenter.model.FlowNodeProp;
import com.hbhb.cw.flowcenter.model.FlowUnit;
import com.hbhb.cw.flowcenter.service.FlowService;
import com.hbhb.cw.flowcenter.web.vo.FlowLineVO;
import com.hbhb.cw.flowcenter.web.vo.FlowNodeVO;
import com.hbhb.cw.flowcenter.web.vo.FlowResVO;
import com.hbhb.cw.flowcenter.web.vo.FlowVO;
import com.hbhb.cw.flowcenter.web.vo.FlowVfdVO;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wangxiaogang
 */
@Service
@Slf4j
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class FlowServiceImpl implements FlowService {

    @Resource
    private FlowMapper flowMapper;
    @Resource
    private FlowNodeMapper flowNodeMapper;
    @Resource
    private FlowLineMapper flowLineMapper;
    @Resource
    private FlowUnitMapper flowUnitMapper;
    @Resource
    private FlowNodePropMapper flowNodePropMapper;
    @Resource
    private FlowNodeNoticeMapper flowNodeNoticeMapper;


    @Override
    public PageResult<FlowResVO> pageFlow(Integer pageNum, Integer pageSize,
                                          String flowName, Long flowTypeId) {
        PageRequest request = DefaultPageRequest.of(pageNum, pageSize);
        return flowMapper.selectPageByCond(flowName, flowTypeId, request);
    }

    @Override
    public FlowVO getFlowInfo(Long flowId) {
        Flow flow = flowMapper.single(flowId);
        List<FlowUnit> flowUnits = flowUnitMapper.createLambdaQuery()
                .andEq(FlowUnit::getFlowId, flowId)
                .select(FlowUnit::getUnitId);
        FlowVO vo = BeanConverter.convert(flow, FlowVO.class);
        vo.setUnitIds(flowUnits.stream().map(FlowUnit::getUnitId).collect(Collectors.toList()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFlow(FlowVO vo) {
        // ??????????????????
        List<Integer> unitIds = vo.getUnitIds();
        if (unitIds.size() == 0) {
            throw new FlowException(FlowErrorCode.FLOW_UNIT_NULL_ERROR);
        }
        // ??????????????????
        Flow flow = new Flow();
        BeanConverter.copyProp(vo, flow);
        flowMapper.insertTemplate(flow);
        // ??????????????????
        flowUnitMapper.insertBatch(
                unitIds.stream().map(unitId -> FlowUnit.builder()
                        .unitId(unitId)
                        .flowId(flow.getId())
                        .build()).collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFlow(FlowVO vo) {
        // ??????????????????
        List<Integer> unitIds = vo.getUnitIds();
        if (unitIds.size() == 0) {
            throw new FlowException(FlowErrorCode.FLOW_UNIT_NULL_ERROR);
        }
        // ??????????????????
        Flow flow = new Flow();
        BeanConverter.copyProp(vo, flow);
        flowMapper.updateTemplateById(flow);
        // ?????????????????????
        flowUnitMapper.createLambdaQuery()
                .andEq(FlowUnit::getFlowId, vo.getId())
                .andIn(FlowUnit::getUnitId, unitIds)
                .delete();
        // ?????????????????????
        flowUnitMapper.insertBatch(
                unitIds.stream().map(unitId -> FlowUnit.builder()
                        .unitId(unitId)
                        .flowId(flow.getId())
                        .build()).collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFlow(Long flowId) {
        Flow flow = new Flow();
        flow.setId(flowId);
        flow.setDeleteFlag(0);
        flowMapper.updateTemplateById(flow);
    }

    @Override
    public void removeFlow(Long flowId) {
        // ????????????
        flowMapper.deleteById(flowId);

        // ??????????????????
        List<FlowLine> flowLines = flowLineMapper.createLambdaQuery()
                .andEq(FlowLine::getFlowId, flowId)
                .select(FlowLine::getId);
        if (flowLines.size() != 0) {
            flowLineMapper.createLambdaQuery()
                    .andIn(FlowLine::getId, flowLines.stream()
                            .map(FlowLine::getId).collect(Collectors.toList()))
                    .delete();
        }

        // ??????????????????????????????
        List<FlowNode> flowNodes = flowNodeMapper.createLambdaQuery()
                .andEq(FlowNode::getFlowId, flowId)
                .select(FlowNode::getId);
        List<String> nodeIds = flowNodes.stream().map(FlowNode::getId).collect(Collectors.toList());
        if (nodeIds.size() != 0) {
            // ????????????
            flowNodeMapper.createLambdaQuery()
                    .andIn(FlowNode::getId, nodeIds)
                    .delete();
            // ??????????????????
            flowNodePropMapper.createLambdaQuery()
                    .andIn(FlowNodeProp::getFlowNodeId, nodeIds)
                    .delete();
            // ??????????????????
            List<FlowNodeNotice> flowNodeNotices = flowNodeNoticeMapper.createLambdaQuery()
                    .andEq(FlowNodeNotice::getFlowNodeId, nodeIds)
                    .select(FlowNodeNotice::getId);
            if (!CollectionUtils.isEmpty(flowNodeNotices)) {
                flowNodeNoticeMapper.createLambdaQuery()
                        .andIn(FlowNodeNotice::getId, flowNodeNotices.stream()
                                .map(FlowNodeNotice::getId).collect(Collectors.toList()))
                        .delete();
            }
        }

        // ??????????????????
        List<FlowUnit> flowUnits = flowUnitMapper.createLambdaQuery()
                .andEq(FlowUnit::getFlowId, flowId)
                .select(FlowUnit::getId);
        flowUnitMapper.createLambdaQuery()
                .andIn(FlowUnit::getId, flowUnits.stream()
                        .map(FlowUnit::getId).collect(Collectors.toList()))
                .delete();
    }

    @Override
    public FlowVfdVO getFlowVfd(Long flowId) {
        Flow flow = flowMapper.single(flowId);
        if (flow == null) {
            return new FlowVfdVO();
        }

        // ????????????????????????
        List<FlowNode> flowNodes = flowNodeMapper.createLambdaQuery()
                .andEq(FlowNode::getFlowId, flowId)
                .asc(FlowNode::getSortNum)
                .select();
        // ????????????????????????
        List<FlowLine> flowLines = flowLineMapper.createLambdaQuery()
                .andEq(FlowLine::getFlowId, flowId)
                .select();

        return FlowVfdVO.builder()
                .flowId(flowId)
                .name(flow.getFlowName())
                .nodeList(Optional.ofNullable(flowNodes)
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(node -> FlowNodeVO.builder()
                                .id(node.getId())
                                .flowId(flowId)
                                .type(node.getNodeType())
                                .name(node.getNodeName())
                                .left(node.getPLeft())
                                .top(node.getPTop())
                                .ico(node.getIco())
                                .state(node.getState()).build())
                        .collect(Collectors.toList()))
                .lineList(Optional.ofNullable(flowLines)
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(line -> FlowLineVO.builder()
                                .id(line.getId())
                                .flowId(flowId)
                                .from(line.getFromNodeId())
                                .to(line.getToNodeId())
                                .label(line.getLabel())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFlowVfd(FlowVfdVO flowVfdVO) {
        Long flowId = flowVfdVO.getFlowId();
        // ????????????????????????
        List<FlowNodeVO> nodeList = flowVfdVO.getNodeList();
        List<FlowNode> flowNodes = Optional.ofNullable(nodeList)
                .orElse(new ArrayList<>())
                .stream()
                .map(nodeVO -> FlowNode.builder()
                        .id(nodeVO.getId())
                        .flowId(flowId)
                        .nodeType(nodeVO.getType())
                        .nodeName(nodeVO.getName())
                        .pLeft(nodeVO.getLeft())
                        .pTop(nodeVO.getTop())
                        .ico(nodeVO.getIco())
                        .state(nodeVO.getState())
                        .sortNum(nodeVO.getSortNum()).build())
                .collect(Collectors.toList());

        // ????????????????????????
        List<FlowLineVO> lineList = flowVfdVO.getLineList();
        List<FlowLine> flowLines = Optional.ofNullable(lineList)
                .orElse(new ArrayList<>())
                .stream()
                .map(lineVO -> FlowLine.builder()
                        .id(lineVO.getId())
                        .flowId(flowId)
                        .fromNodeId(lineVO.getFrom())
                        .toNodeId(lineVO.getTo())
                        .label(lineVO.getLabel())
                        .build())
                .collect(Collectors.toList());

        List<String> flowNodeIds = flowNodes.stream().map(FlowNode::getId).collect(Collectors.toList());
        List<String> flowLineIds = flowLines.stream().map(FlowLine::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(flowNodes)) {
            // ?????????
            flowNodeMapper.createLambdaQuery().andIn(FlowNode::getId, flowNodeIds).delete();
            // ?????????
            flowNodeMapper.batchInsert(flowNodes.stream().map(node ->
                    FlowNodeVO.builder()
                            .id(node.getId())
                            .flowId(node.getFlowId())
                            .name(node.getNodeName())
                            .type(node.getNodeType())
                            .left(node.getPLeft())
                            .top(node.getPTop())
                            .ico(node.getIco())
                            .state(node.getState())
                            .sortNum(node.getSortNum())
                            .build())
                    .collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(flowLines)) {
            // ?????????
            flowLineMapper.createLambdaQuery().andIn(FlowLine::getId, flowLineIds).delete();
            // ?????????
            flowLineMapper.batchInsert(flowLines.stream().map(line ->
                    FlowLineVO.builder()
                            .id(line.getId())
                            .flowId(line.getFlowId())
                            .from(line.getFromNodeId())
                            .to(line.getToNodeId())
                            .label(line.getLabel())
                            .build())
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public List<SelectVO> getFlowNameList() {
        List<Flow> flows = flowMapper.createLambdaQuery()
                .andEq(Flow::getDeleteFlag, 1)
                .select(Flow::getId, Flow::getFlowName);
        return Optional.ofNullable(flows)
                .orElse(new ArrayList<>())
                .stream()
                .map(flow -> SelectVO.builder()
                        .id(flow.getId())
                        .label(flow.getFlowName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Flow getFlowById(Long flowId) {
        return flowMapper.single(flowId);
    }

    @Override
    public String getNameByNodeId(String nodeId) {
        return flowMapper.selectNameByNodeId(nodeId);
    }

    @Override
    public List<Flow> getFlowsByTypeId(Long typeId) {
        return flowMapper.createLambdaQuery()
                .andEq(Flow::getFlowTypeId, typeId)
                .andEq(Flow::getDeleteFlag, 1)
                .select();
    }

    @Override
    public Map<Long, String> getFlowNameByIds(List<Long> ids) {
        List<Flow> select = flowMapper.createLambdaQuery()
                .andIn(Flow::getId, ids)
                .select(Flow::getId, Flow::getFlowName);
        return select.stream()
                .collect(Collectors
                        .toMap(Flow::getId, Flow::getFlowName));
    }

    @Override
    public List<SelectVO> getFlowNameByTypeId(Long typeId) {
        List<Flow> flows = flowMapper.createLambdaQuery()
                .andEq(Flow::getDeleteFlag, 1)
                .andEq(Flow::getFlowTypeId, typeId)
                .select(Flow::getId, Flow::getFlowName);
        return Optional.ofNullable(flows)
                .orElse(new ArrayList<>())
                .stream()
                .map(flow -> SelectVO.builder()
                        .id(flow.getId())
                        .label(flow.getFlowName())
                        .build())
                .collect(Collectors.toList());
    }
}
