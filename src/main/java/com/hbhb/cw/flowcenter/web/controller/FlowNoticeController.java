package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.cw.flowcenter.api.FlowNoticeApi;
import com.hbhb.cw.flowcenter.service.NoticeService;
import com.hbhb.cw.flowcenter.vo.FlowNodeNoticeResVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxiaogang
 */
@Tag(name = "流程相关-提醒")
@RestController
@RequestMapping("/flow/notice")
public class FlowNoticeController implements FlowNoticeApi {
    @Resource
    private NoticeService noticeService;

    @Override
    public List<FlowNodeNoticeResVO> getFlowNodeNotice(String nodeId) {
        return noticeService.getFlowNodeNotice(nodeId);
    }
}
