package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.cw.flowcenter.mapper.FlowMapper;
import com.hbhb.cw.flowcenter.model.Flow;
import com.hbhb.cw.flowcenter.service.FlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wangxiaogang
 */
@Service
@Slf4j
public class FlowServiceImpl implements FlowService {
    @Resource
    private FlowMapper flowMapper;

    @Override
    public Flow getFlow(Long flowId) {
        return flowMapper.lock(flowId);
    }

    @Override
    public String getNameByNodeId(String flowNodeId) {
        return flowMapper.selectNameByNodeId(flowNodeId);
    }
}
