package com.hbhb.cw.flowcenter.service;

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

}
