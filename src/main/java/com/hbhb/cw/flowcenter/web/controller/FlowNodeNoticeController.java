package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.cw.flowcenter.api.FlowNodeNoticeApi;
import com.hbhb.cw.flowcenter.service.FlowNodeNoticeService;
import com.hbhb.cw.flowcenter.vo.FlowNodeNoticeVO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author wangxiaogang
 */
@Tag(name = "流程节点提醒")
@RestController
@RequestMapping("/node/notice")
public class FlowNodeNoticeController implements FlowNodeNoticeApi {

    @Resource
    private FlowNodeNoticeService flowNodeNoticeService;

    @Operation(summary = "查看节点提醒列表")
    @Override
    public List<FlowNodeNoticeVO> getNodeNoticeList(
            @Parameter(description = "流程节点id") @RequestParam String flowNodeId) {
        return flowNodeNoticeService.getNodeNoticeList(flowNodeId);
    }

    @Operation(summary = "新增节点提醒")
    @PostMapping("")
    public void addNodeNotice(
            @Parameter(description = "节点提醒列表") @RequestBody List<FlowNodeNoticeVO> list) {
        flowNodeNoticeService.addNodeNotice(list);
    }

    @Operation(summary = "修改节点提醒")
    @PutMapping("")
    public void updateNodeNotice(
            @Parameter(description = "节点提醒实体") @RequestBody FlowNodeNoticeVO vo) {
        flowNodeNoticeService.updateNodeNotice(vo);
    }

    @Operation(summary = "删除节点提醒")
    @DeleteMapping("/{id}")
    public void deleteFlowNodeNotice(@Parameter(description = "节点提醒id") @PathVariable Long id) {
        flowNodeNoticeService.deleteNodeNotice(id);
    }
}
