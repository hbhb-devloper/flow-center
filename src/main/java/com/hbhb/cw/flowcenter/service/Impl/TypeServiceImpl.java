package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.cw.flowcenter.mapper.TypeMapper;
import com.hbhb.cw.flowcenter.model.Flow;
import com.hbhb.cw.flowcenter.service.TypeService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangxiaogang
 */
@Service
@Slf4j
public class TypeServiceImpl implements TypeService {
    @Resource
    private TypeMapper typeMapper;

    @Override
    public Long getIdByNodeId(String flowNodeId) {
        return typeMapper.selectIdByNodeId(flowNodeId);
    }

    @Override
    public List<Flow> getFlowsByTypeId(Long id) {
       return typeMapper.selectFlowByTypeId(id);
    }
}
