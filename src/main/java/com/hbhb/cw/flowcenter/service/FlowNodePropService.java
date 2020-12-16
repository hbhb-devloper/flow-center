package com.hbhb.cw.flowcenter.service;


import com.hbhb.cw.flowcenter.vo.FlowNodePropVO;

import java.util.List;

public interface FlowNodePropService {

    /**
     * 根据节点id获取节点属性详情
     */
    FlowNodePropVO getNodePropInfo(String flowNodeId);

    /**
     * 新增节点属性
     */
    void addNodeProp(FlowNodePropVO vo, Integer userId);

    /**
     * 修改节点属性
     */
    void updateNodeProp(FlowNodePropVO vo);

    /**
     * 删除节点属性
     */
    void deleteNodeProp(Long id);

    /**
     * 按流程id获取对应节点属性列表
     */
    List<FlowNodePropVO> getNodeProps(Long flowId);
}