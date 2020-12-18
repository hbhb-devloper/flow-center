package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.core.bean.BeanConverter;
import com.hbhb.core.bean.SelectVO;
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

import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    @Resource
    private JdbcTemplate jdbcTemplate;

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
        // 校验关联单位
        List<Integer> unitIds = vo.getUnitIds();
        if (unitIds.size() == 0) {
            throw new FlowException(FlowErrorCode.FLOW_UNIT_NULL_ERROR);
        }
        // 新增主表数据
        Flow flow = new Flow();
        BeanConverter.copyProp(vo, flow);
        flowMapper.insertTemplate(flow);
        // 新增关联数据
        flowUnitMapper.insertBatch(
                unitIds.stream().map(unitId -> FlowUnit.builder()
                        .unitId(unitId)
                        .flowId(flow.getId())
                        .build()).collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFlow(FlowVO vo) {
        // 校验关联单位
        List<Integer> unitIds = vo.getUnitIds();
        if (unitIds.size() == 0) {
            throw new FlowException(FlowErrorCode.FLOW_UNIT_NULL_ERROR);
        }
        // 修改主表数据
        Flow flow = new Flow();
        BeanConverter.copyProp(vo, flow);
        flowMapper.updateTemplateById(flow);
        // 先删除关联数据
        flowUnitMapper.createLambdaQuery()
                .andEq(FlowUnit::getFlowId, vo.getId())
                .andIn(FlowUnit::getUnitId, unitIds)
                .delete();
        // 再新增关联数据
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
        // 删除流程
        flowMapper.deleteById(flowId);

        // 删除流程链接
        List<FlowLine> flowLines = flowLineMapper.createLambdaQuery()
                .andEq(FlowLine::getFlowId, flowId)
                .select(FlowLine::getId);
        if (flowLines.size() != 0) {
            flowLineMapper.createLambdaQuery()
                    .andIn(FlowLine::getId, flowLines.stream()
                            .map(FlowLine::getId).collect(Collectors.toList()))
                    .delete();
        }

        // 删除流程节点相关数据
        List<FlowNode> flowNodes = flowNodeMapper.createLambdaQuery()
                .andEq(FlowNode::getFlowId, flowId)
                .select(FlowNode::getId);
        List<String> nodeIds = flowNodes.stream().map(FlowNode::getId).collect(Collectors.toList());
        if (nodeIds.size() != 0) {
            // 删除节点
            flowNodeMapper.createLambdaQuery()
                    .andIn(FlowNode::getId, nodeIds)
                    .delete();
            // 删除节点属性
            flowNodePropMapper.createLambdaQuery()
                    .andIn(FlowNodeProp::getFlowNodeId, nodeIds)
                    .delete();
            // 删除节点提醒
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

        // 删除流程单位
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

        // 组装流程节点信息
        List<FlowNode> flowNodes = flowNodeMapper.createLambdaQuery()
                .andEq(FlowNode::getFlowId, flowId)
                .asc(FlowNode::getSortNum)
                .select();
        // 组装流程链接信息
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
        // 组装流程节点信息
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

        // 组装流程链接信息
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
            // 先删除
            flowNodeMapper.createLambdaQuery().andIn(FlowNode::getId, flowNodeIds).delete();
            // 再保存
            // 此处由于节点表的id在设计之初为非自增主键，不符合beetlsql的要求，无法直接使用mapper接口
            // 有三种方案：
            // 1.最直接的做法，修改数据库表字段名，但重构工作量大
            // 2.使用beetlsql的SQLManager.executeBatchUpdate，需要在BaseMapper中定义新的方法，可行但较为麻烦
            // 3.使用原生jdbc，最简单
            String sql = "insert into flow_node (id,flow_id,node_type,node_name,p_left,p_top,ico,state,sort_num) values (?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.batchUpdate(sql, flowNodes, 1, (ps, argument) -> {
                ps.setString(1, argument.getId());
                ps.setLong(2, argument.getFlowId());
                ps.setString(3, argument.getNodeType());
                ps.setString(4, argument.getNodeName());
                ps.setString(5, argument.getPLeft());
                ps.setString(6, argument.getPTop());
                ps.setString(7, argument.getIco());
                ps.setString(8, argument.getState());
                ps.setInt(9, argument.getSortNum());
            });
        }
        if (!CollectionUtils.isEmpty(flowLines)) {
            // 先删除
            flowLineMapper.createLambdaQuery().andIn(FlowLine::getId, flowLineIds).delete();
            // 再保存
            String sql = "insert into flow_line (id,flow_id,from_node_id,to_node_id,label) values (?,?,?,?,?)";
            jdbcTemplate.batchUpdate(sql, flowLines, 1, (ps, argument) -> {
                ps.setString(1, argument.getId());
                ps.setLong(2, argument.getFlowId());
                ps.setString(3, argument.getFromNodeId());
                ps.setString(4, argument.getToNodeId());
                ps.setString(5, argument.getLabel());
            });
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
}
