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
import com.hbhb.cw.flowcenter.rpc.DictApiExp;
import com.hbhb.cw.flowcenter.rpc.UnitApiExp;
import com.hbhb.cw.flowcenter.rpc.UserApiExp;
import com.hbhb.cw.flowcenter.service.FlowService;
import com.hbhb.cw.flowcenter.web.vo.FlowExportVO;
import com.hbhb.cw.flowcenter.web.vo.FlowLineVO;
import com.hbhb.cw.flowcenter.web.vo.FlowNodeVO;
import com.hbhb.cw.flowcenter.web.vo.FlowProjectVO;
import com.hbhb.cw.flowcenter.web.vo.FlowResVO;
import com.hbhb.cw.flowcenter.web.vo.FlowStatisticsResVO;
import com.hbhb.cw.flowcenter.web.vo.FlowVO;
import com.hbhb.cw.systemcenter.enums.DictCode;
import com.hbhb.cw.systemcenter.enums.TypeCode;
import com.hbhb.cw.systemcenter.vo.DictVO;

import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private DictApiExp dictApi;
    @Resource
    private UnitApiExp unitApi;
    @Resource
    private UserApiExp userApi;

    @Override
    public PageResult<FlowResVO> pageFlow(Integer pageNum, Integer pageSize,
                                          String flowName, Long flowTypeId) {
        PageRequest request = DefaultPageRequest.of(pageNum, pageSize);
        return flowMapper.selectPageByCond(flowName, flowTypeId, request);
    }

    @Override
    public PageResult<FlowStatisticsResVO> pageFlowInfo(Integer pageNum, Integer pageSize,
                                                        Integer unitId, Long flowId) {
        PageRequest request = DefaultPageRequest.of(pageNum, pageSize);
        PageResult<FlowStatisticsResVO> pageResult = flowMapper.selectInfoPageByCond(unitId, flowId, request);

        // 数据处理
        List<FlowStatisticsResVO> list = pageResult.getList();
        if (!CollectionUtils.isEmpty(list)) {
            // 字典
            List<DictVO> dictList = dictApi.getDict(
                    TypeCode.FLOW.value(), DictCode.FLOW_NODE_PROP_ENABLE_COND.value());
            Map<String, String> dictMap = dictList.stream().collect(
                    Collectors.toMap(DictVO::getValue, DictVO::getLabel));
            // 单位
            Map<Integer, String> unitMap = unitApi.getUnitMapById();
            // 用户
            List<Integer> userIds = list.stream().map(FlowStatisticsResVO::getUserId).collect(Collectors.toList());
            Map<Integer, String> userMap = userApi.getUserMapById(userIds);

            list.forEach(vo -> {
                vo.setUnitName(unitMap.get(vo.getUnitId()));
                vo.setRoleRange(unitMap.get(vo.getRoleRangeId()));
                vo.setUserName(userMap.get(vo.getUserId()));
                vo.setEnableCond(dictMap.get(vo.getEnableCond()));
            });
        }

        return pageResult;
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
        flowMapper.insertTemplate(BeanConverter.convert(vo, Flow.class));
        // 新增关联数据
        flowUnitMapper.insertBatch(
                unitIds.stream().map(unitId -> FlowUnit.builder()
                        .unitId(unitId)
                        .flowId(vo.getId())
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
        flowMapper.updateTemplateById(BeanConverter.convert(vo, Flow.class));
        // 先删除关联数据
        flowUnitMapper.createLambdaQuery()
                .andEq(FlowUnit::getFlowId, vo.getId())
                .andIn(FlowUnit::getUnitId, unitIds)
                .delete();
        // 再新增关联数据
        flowUnitMapper.insertBatch(
                unitIds.stream().map(unitId -> FlowUnit.builder()
                        .unitId(unitId)
                        .flowId(vo.getId())
                        .build()).collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFlow(Long flowId) {
        // todo 需要各模块api
//        // 判断该流程是否被使用中
//        boolean flag = budgetProjectService.isUseFlowId(flowId);
//        if (flag) {
//            throw new FlowException(FlowErrorCode.FLOW_IS_IN_USE);
//        }
        Flow flow = new Flow();
        flow.setId(flowId);
        flow.setDeleteFlag(0);
        flowMapper.updateTemplateById(flow);
    }

    @Override
    public void removeFlow(Long flowId) {
//        // 判断该流程是否被使用中
//        boolean flag = budgetProjectService.isUseFlowId(flowId);
//        if (flag) {
//            throw new FlowException(FlowErrorCode.FLOW_IS_IN_USE);
//        }

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
    public FlowProjectVO getFlowProjectInfo(Long flowId) {
        Flow flow = flowMapper.single(flowId);
        if (flow == null) {
            return new FlowProjectVO();
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

        return FlowProjectVO.builder()
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
    public void saveFlowProjectInfo(FlowProjectVO flowProjectVO) {
        Long flowId = flowProjectVO.getFlowId();
        // 组装流程节点信息
        List<FlowNodeVO> nodeList = flowProjectVO.getNodeList();
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
        List<FlowLineVO> lineList = flowProjectVO.getLineList();
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
            flowNodeMapper.insertBatch(flowNodes);
        }
        if (!CollectionUtils.isEmpty(flowLines)) {
            // 先删除
            flowLineMapper.createLambdaQuery().andIn(FlowLine::getId, flowLineIds).delete();
            // 再保存
            flowLineMapper.insertBatch(flowLines);
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
    public List<FlowExportVO> getExportList(Integer unitId, Long flowId) {
        PageResult<FlowStatisticsResVO> pageResult = this.pageFlowInfo(
                1, Integer.MAX_VALUE, unitId, flowId);
        if (CollectionUtils.isEmpty(pageResult.getList())) {
            return new ArrayList<>();
        }
        return pageResult.getList().stream()
                .map(vo -> BeanConverter.convert(vo, FlowExportVO.class))
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
