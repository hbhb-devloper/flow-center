package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.api.FlowNodeApi;
import com.hbhb.cw.flowcenter.model.FlowLine;
import com.hbhb.cw.flowcenter.service.FlowNodeService;
import com.hbhb.cw.flowcenter.web.vo.FlowNodeVO;

import org.beetl.sql.core.page.PageResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Tag(name = "流程节点")
@RestController
@RequestMapping("/node")
public class FlowNodeController implements FlowNodeApi {

    @Resource
    private FlowNodeService flowNodeService;

    @Operation(summary = "（分页）获取流程节点列表", description = "新版本 该接口用于何处")
    @GetMapping("/list")
    public PageResult<FlowNodeVO> pageFlowNode(
            @Parameter(description = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
            @Parameter(description = "每页数量,默认为10 ") @RequestParam(required = false) Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        return flowNodeService.pageFlowNode(pageNum, pageSize);
    }

    @Operation(summary = "删除流程节点", description = "新版本 /delete/{id} -> /{id}")
    @DeleteMapping("/{id}")
    public void deleteFlowNode(@Parameter(description = "节点id") @PathVariable String id) {
        flowNodeService.deleteFlowNode(id);
    }

    @Operation(summary = "删除节点链接线", description = "新版本 /delete/line -> /line")
    @DeleteMapping("/line")
    public void deleteFlowNodeLine(
            @Parameter(description = "开始节点id", required = true) @RequestParam String fromNodeId,
            @Parameter(description = "结束节点id", required = true) @RequestParam String toNodeId) {
        FlowLine flowLine = flowNodeService.getNodeLine(fromNodeId, toNodeId);
        flowNodeService.deleteNodeLine(flowLine.getId());
    }

    @Operation(summary = "流程节点名称列表", description = "新版本 /name/{flowId} -> /select")
    @GetMapping("/select")
    public List<SelectVO> getFlowNodeName(@Parameter(description = "流程id") @RequestParam Long flowId) {
        return flowNodeService.getNodeMap(flowId);
    }

    @Operation(summary = "获取流程节点数")
    @Override
    public long getNodeNum(@Parameter(description = "流程id") Long flowId) {
        return flowNodeService.getNodeNum(flowId);
    }
}
