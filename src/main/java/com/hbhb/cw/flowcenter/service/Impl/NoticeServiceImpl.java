package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.cw.flowcenter.mapper.NoticeMapper;
import com.hbhb.cw.flowcenter.service.NoticeService;
import com.hbhb.cw.flowcenter.vo.FlowNodeNoticeResVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxiaogang
 */
@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public List<FlowNodeNoticeResVO> getFlowNodeNotice(String flowNodeId) {
        return noticeMapper.selectNoticeByFlowNodeId(flowNodeId);
    }
}
