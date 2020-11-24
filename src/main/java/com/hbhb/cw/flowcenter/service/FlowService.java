package com.hbhb.cw.flowcenter.service;

import com.hbhb.cw.flowcenter.model.Flow;

/**
 * @author wangxiaogang
 */
public interface FlowService {
    /**
     * 跟据流程id获取流程信息
     *
     * @param flowId 流程id
     * @return 流程实体信息
     */
    Flow getFlow(Long flowId);

    /**
     * 通过节点id得到流程名称
     *
     * @param flowNodeId 节点id
     * @return 流程名称
     */
    String getNameByNodeId(String flowNodeId);
}
