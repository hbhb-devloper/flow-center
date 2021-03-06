package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.beetlsql.core.QueryExt;
import com.hbhb.cw.flowcenter.mapper.FlowTypeMapper;
import com.hbhb.cw.flowcenter.model.FlowType;
import com.hbhb.cw.flowcenter.service.FlowTypeService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.Query;
import org.springframework.stereotype.Service;

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
@SuppressWarnings(value = {"unchecked"})
public class FlowTypeServiceImpl implements FlowTypeService {

    @Resource
    private FlowTypeMapper flowTypeMapper;

    @Override
    public PageResult<FlowType> pageFlowType(Integer pageNum, Integer pageSize, String flowTypeName) {
        return flowTypeMapper.createLambdaQuery()
                .andLike(FlowType::getFlowTypeName, QueryExt.filterLikeEmpty(flowTypeName))
                .asc(FlowType::getSortNum)
                .page(pageNum, pageSize);
    }

    @Override
    public List<SelectVO> getListByModule(String module) {
        List<FlowType> flowTypes = flowTypeMapper.createLambdaQuery()
                .andEq(FlowType::getModule, Query.filterEmpty(module))
                .select();
        return Optional.ofNullable(flowTypes)
                .orElse(new ArrayList<>())
                .stream()
                .map(flowType -> SelectVO.builder()
                        .id(flowType.getId())
                        .label(flowType.getFlowTypeName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public FlowType getFlowTypeInfo(Long id) {
        return flowTypeMapper.single(id);
    }

    @Override
    public void upsertFlowType(FlowType flowType) {
        flowTypeMapper.upsertByTemplate(flowType);
    }

    @Override
    public void deleteFlowType(Long flowTypeId) {
        flowTypeMapper.deleteById(flowTypeId);
    }

    @Override
    public Long getIdByNodeId(String id) {
        return flowTypeMapper.selectIdByNodeId(id);
    }

    @Override
    public Map<Long, String> getFlowTypeNameByIds(List<Long> ids) {

        List<FlowType> select = flowTypeMapper.createLambdaQuery()
                .andIn(FlowType::getId, ids)
                .select(FlowType::getId, FlowType::getFlowTypeName);
        return select.stream()
                .collect(Collectors
                        .toMap(FlowType::getId, FlowType::getFlowTypeName));

    }
}
