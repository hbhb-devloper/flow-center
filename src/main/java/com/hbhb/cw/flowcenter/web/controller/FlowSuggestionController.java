package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.cw.flowcenter.model.FlowSuggestion;
import com.hbhb.cw.flowcenter.service.FlowSuggestionService;
import com.hbhb.cw.flowcenter.web.vo.FlowSuggestionVO;

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

@Tag(name = "流程审批意见")
@RestController
@RequestMapping("/suggestion")
public class FlowSuggestionController {

    @Resource
    private FlowSuggestionService flowSuggestionService;

    @Operation(summary = "获取审批意见列表")
    @GetMapping("/list")
    public List<FlowSuggestion> getSuggestionList() {
        return flowSuggestionService.getSuggestionList();
    }

    @Operation(summary = "获取审批意见详情 | 新版本 /info/{id} -> /{id}")
    @GetMapping("/{id}")
    public FlowSuggestion getSuggestionInfo(@Parameter(description = "审批意见id") @PathVariable Long id) {
        return flowSuggestionService.getSuggestionInfo(id);
    }

    @Operation(summary = "新增审批意见")
    @PostMapping("")
    public void addSuggestion(
            @Parameter(description = "审批意见实体") @RequestBody FlowSuggestionVO vo) {
        flowSuggestionService.upsertSuggestion(vo);
    }

    @Operation(summary = "修改审批意见 | 新版本 /update -> /")
    @PutMapping("")
    public void updateSuggestion(
            @Parameter(description = "审批意见实体") @RequestBody FlowSuggestionVO vo) {
        flowSuggestionService.upsertSuggestion(vo);
    }

    @Operation(summary = "删除审批意见")
    @DeleteMapping("/delete/{id}")
    public void deleteSuggestion(@Parameter(description = "审批意见id") @PathVariable Long id) {
        flowSuggestionService.deleteSuggestion(id);
    }
}
