package com.hbhb.cw.flowcenter.service;

import com.hbhb.cw.flowcenter.vo.FlowNodeNoticeResVO;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface NoticeService {
    /**
     * 获取节点提醒列表
     *
     * @param flowNodeId 节点id
     * @return 节点提醒对象
     */
    List<FlowNodeNoticeResVO> getFlowNodeNotice(String flowNodeId);
}
