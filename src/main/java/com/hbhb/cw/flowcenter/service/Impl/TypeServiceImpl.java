package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.cw.flowcenter.mapper.TypeMapper;
import com.hbhb.cw.flowcenter.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
