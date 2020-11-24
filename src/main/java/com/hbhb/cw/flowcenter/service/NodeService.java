package com.hbhb.cw.flowcenter.service;

import com.hbhb.cw.flowcenter.vo.SelectVO;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface NodeService {
    /**
     * 跟据流程id获取流程节点信息
     *
     * @param flowId 流程id
     * @return 节点id名称
     */
    List<SelectVO> getFlowNodeName(Long flowId);

    /**
     * 获取流程的节点数
     *
     * @param id 流程id
     * @return 节点数量
     */
    int countFlowNode(Long id);

}
