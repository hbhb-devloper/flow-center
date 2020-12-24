package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.api.FlowNodePropApi;
import com.hbhb.cw.flowcenter.service.FlowNodePropService;
import com.hbhb.cw.flowcenter.vo.FlowNodePropVO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "流程节点属性")
@RestController
@RequestMapping("/node/prop")
public class FlowNodePropController implements FlowNodePropApi {

    @Resource
    private FlowNodePropService flowNodePropService;

    @Operation(summary = "获取节点属性详情")
    @GetMapping("/info")
    public FlowNodePropVO getFlowNodeProp(
            @Parameter(description = "流程节点id") @RequestParam String flowNodeId) {
        return flowNodePropService.getNodePropInfo(flowNodeId);
    }

    @Operation(summary = "新增/修改节点属性")
    @PostMapping("")
    public void updateFlowProp(@Parameter(description = "节点属性vo") @RequestBody FlowNodePropVO vo) {
        flowNodePropService.upsertNodeProp(vo);
    }

    @Operation(summary = "删除节点属性")
    @DeleteMapping("/delete/{id}")
    public void deleteFlowNodeProp(@Parameter(description = "节点属性id") @PathVariable Long id) {
        flowNodePropService.deleteNodeProp(id);
    }

    @Operation(summary = "获取某流程中的分配者列表")
    @GetMapping("/assigner")
    public List<SelectVO> getAssignerList(@Parameter(description = "流程id") @RequestParam Long flowId) {
        return flowNodePropService.getAssignerList(flowId);
    }

    @Operation(summary = "获取节点属性列表")
    @Override
    public List<FlowNodePropVO> getNodeProps(@Parameter(description = "流程id") Long flowId) {
        return flowNodePropService.getNodeProps(flowId);
    }
}
