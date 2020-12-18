package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.cw.flowcenter.service.FlowNoticeService;
import com.hbhb.cw.flowcenter.web.vo.FlowNoticeVO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "流程提醒库")
@RestController
@RequestMapping("/notice")
public class FlowNoticeController {

    @Resource
    private FlowNoticeService flowNoticeService;

    @Operation(summary = "查询流程提醒列表")
    @GetMapping("/list")
    public List<FlowNoticeVO> getNoticeTemplateList() {
        return flowNoticeService.getNoticeList();
    }

    @Operation(summary = "流程提醒详情")
    @GetMapping("/{id}")
    public FlowNoticeVO getFlowRemind(@Parameter(description = "主键id") @PathVariable Long id) {
        return flowNoticeService.getNoticeInfo(id);
    }

    @Operation(summary = "新增提醒")
    @PostMapping("")
    public void addFlowRemind(@Parameter(description = "流程提醒实体") @RequestBody FlowNoticeVO vo) {
        flowNoticeService.upsertNotice(vo);
    }

    @Operation(summary = "修改提醒")
    @PutMapping("")
    public void updateFlowRemind(@Parameter(description = "流程提醒实体") @RequestBody FlowNoticeVO vo) {
        flowNoticeService.upsertNotice(vo);
    }

    @Operation(summary = "删除流程提醒")
    @DeleteMapping("/delete/{id}")
    public void deleteFlowRemind(@Parameter(description = "主键id") @PathVariable Long id) {
        flowNoticeService.deleteNotice(id);
    }
}
