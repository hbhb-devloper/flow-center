package com.hbhb.cw.flowcenter.service;

import com.hbhb.cw.flowcenter.web.vo.FlowNoticeVO;

import java.util.List;

public interface FlowNoticeService {

    /**
     * 查询提醒列表
     */
    List<FlowNoticeVO> getNoticeList();

    /**
     * 查询提醒详情
     */
    FlowNoticeVO getNoticeInfo(Long id);

    /**
     * 新增/修改流程提醒
     */
    void upsertNotice(FlowNoticeVO vo);

    /**
     * 删除流程提醒
     */
    void deleteNotice(Long id);
}
