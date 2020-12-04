package com.hbhb.cw.flowcenter.service;

import com.hbhb.cw.flowcenter.model.Flow;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface TypeService {
    /**
     * 跟据节点id获取流程类型id
     *
     * @param flowNodeId 节点id
     * @return 流程类型id
     */
    Long getIdByNodeId(String flowNodeId);

    /**
     * 根据流程类型id得到流程id
     */
    List<Flow> getFlowsByTypeId(Long id);
}
