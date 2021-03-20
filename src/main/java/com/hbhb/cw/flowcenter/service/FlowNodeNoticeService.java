package com.hbhb.cw.flowcenter.service;

import com.hbhb.cw.flowcenter.vo.FlowNodeNoticeVO;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface FlowNodeNoticeService {

    /**
     * 获取节点提醒列表
     */
    List<FlowNodeNoticeVO> getNodeNoticeList(String flowNodeId);

    /**
     * 新增节点提醒
     */
    void addNodeNotice(List<FlowNodeNoticeVO> list);

    /**
     * 修改节点提醒
     */
    void updateNodeNotice(FlowNodeNoticeVO vo);

    /**
     * 删除节点提醒
     */
    void deleteNodeNotice(Long id);
}
