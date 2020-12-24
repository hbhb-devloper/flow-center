package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.core.bean.BeanConverter;
import com.hbhb.cw.flowcenter.mapper.FlowLineMapper;
import com.hbhb.cw.flowcenter.mapper.FlowNodeMapper;
import com.hbhb.cw.flowcenter.mapper.FlowNodePropMapper;
import com.hbhb.cw.flowcenter.model.FlowLine;
import com.hbhb.cw.flowcenter.model.FlowNode;
import com.hbhb.cw.flowcenter.model.FlowNodeProp;
import com.hbhb.cw.flowcenter.rpc.DictApiExp;
import com.hbhb.cw.flowcenter.rpc.UnitApiExp;
import com.hbhb.cw.flowcenter.service.FlowNodeService;
import com.hbhb.cw.flowcenter.web.vo.FlowExportVO;
import com.hbhb.cw.flowcenter.web.vo.FlowNodeResVO;
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
public class FlowNodeServiceImpl implements FlowNodeService {

    @Resource
    private FlowNodeMapper flowNodeMapper;
    @Resource
    private FlowLineMapper flowLineMapper;
    @Resource
    private FlowNodePropMapper flowNodePropMapper;
    @Resource
    private DictApiExp dictApi;
    @Resource
    private UnitApiExp unitApi;

    @Override
    public PageResult<FlowNodeResVO> pageFlowNode(Integer pageNum, Integer pageSize,
                                                  Integer unitId, Long flowId) {
        PageRequest request = DefaultPageRequest.of(pageNum, pageSize);
        PageResult<FlowNodeResVO> pageResult = flowNodeMapper.selectPageByCond(unitId, flowId, request);

        // 数据处理
        List<FlowNodeResVO> list = pageResult.getList();
        if (!CollectionUtils.isEmpty(list)) {
            // 字典
            List<DictVO> dictList = dictApi.getDict(
                    TypeCode.FLOW.value(), DictCode.FLOW_NODE_PROP_ENABLE_COND.value());
            Map<String, String> dictMap = dictList.stream().collect(
                    Collectors.toMap(DictVO::getValue, DictVO::getLabel));
            // 单位
            Map<Integer, String> unitMap = unitApi.getUnitMapById();

            list.forEach(vo -> {
                vo.setUnitName(unitMap.get(vo.getUnitId()));
                vo.setRoleRange(unitMap.get(vo.getRoleRangeId()));
                vo.setEnableCond(dictMap.get(vo.getEnableCond()));
            });
        }
        return pageResult;
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
    public List<FlowExportVO> getExportList(Integer unitId, Long flowId) {
        PageResult<FlowNodeResVO> pageResult = this.pageFlowNode(
                1, Integer.MAX_VALUE, unitId, flowId);
        if (CollectionUtils.isEmpty(pageResult.getList())) {
            return new ArrayList<>();
        }
        return pageResult.getList().stream()
                .map(vo -> BeanConverter.convert(vo, FlowExportVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public long getNodeNum(Long flowId) {
        return flowNodeMapper.createLambdaQuery().andEq(FlowNode::getFlowId, flowId).count();
    }
}
