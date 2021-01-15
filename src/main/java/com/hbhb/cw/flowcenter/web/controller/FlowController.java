package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.api.FlowApi;
import com.hbhb.cw.flowcenter.model.Flow;
import com.hbhb.cw.flowcenter.service.FlowService;
import com.hbhb.cw.flowcenter.web.vo.FlowResVO;
import com.hbhb.cw.flowcenter.web.vo.FlowVO;
import com.hbhb.cw.flowcenter.web.vo.FlowVfdVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.beetl.sql.core.page.PageResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author wangxiaogang
 */
@Tag(name = "流程")
@RestController
@RequestMapping("")
public class FlowController implements FlowApi {

    @Resource
    private FlowService flowService;

    @Operation(summary = "获取流程列表（分页）")
    @GetMapping("/list")
    public PageResult<FlowResVO> pageFlow(
            @Parameter(description = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
            @Parameter(description = "每页数量，默认为10") @RequestParam(required = false) Integer pageSize,
            @Parameter(description = "流程名称") @RequestParam(required = false) String flowName,
            @Parameter(description = "流程类型id") @RequestParam(required = false) Long flowTypeId) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        return flowService.pageFlow(pageNum, pageSize, flowName, flowTypeId);
    }

    @Operation(summary = "获取流程详情")
    @GetMapping("/{flowId}")
    public FlowVO getFlowInfo(@Parameter(description = "流程id", required = true) @PathVariable Long flowId) {
        return flowService.getFlowInfo(flowId);
    }

    @Operation(summary = "新增流程")
    @PostMapping("")
    public void addFlow(@Parameter(description = "流程实体") @RequestBody FlowVO vo) {
        flowService.addFlow(vo);
    }

    @Operation(summary = "更新流程")
    @PutMapping("")
    public void updateFlow(@Parameter(description = "流程实体") @RequestBody FlowVO vo) {
        flowService.updateFlow(vo);
    }

    @Operation(summary = "删除流程")
    @DeleteMapping("/{flowId}")
    public void deleteFlow(@PathVariable Long flowId) {
        flowService.deleteFlow(flowId);
    }

    @Operation(summary = "获取流程图")
    @GetMapping("/vfd/{flowId}")
    public FlowVfdVO getFlowVfd(
            @Parameter(description = "流程id", required = true) @PathVariable Long flowId) {
        return flowService.getFlowVfd(flowId);
    }

    @Operation(summary = "保存流程图")
    @PostMapping("/vfd")
    public void saveFlowVfd(
            @Parameter(description = "流程项目详情", required = true) @RequestBody FlowVfdVO vo) {
        flowService.saveFlowVfd(vo);
    }

    @Operation(summary = "流程名称列表")
    @GetMapping("/select")
    public List<SelectVO> getFlowNameList() {
        return flowService.getFlowNameList();
    }

    @Operation(summary = "按id获取流程详情")
    @Override
    public Flow getFlowById(@Parameter(description = "流程id", required = true) Long flowId) {
        return flowService.getFlowById(flowId);
    }

    @Operation(summary = "按节点id获取流程名称")
    @Override
    public String getNameByNodeId(@Parameter(description = "节点id", required = true) String nodeId) {
        return flowService.getNameByNodeId(nodeId);
    }

    @Operation(summary = "按类型id获取流程列表")
    @Override
    public List<Flow> getFlowsByTypeId(@Parameter(description = "类型id", required = true) Long typeId) {
        return flowService.getFlowsByTypeId(typeId);
    }

    @Override
    @Operation(summary = "流程名称")
    public Map<Long, String> getFlowMapName(@Parameter(description = "流程id列表", required = true)
                                                    List<Long> ids) {
        return flowService.getFlowNameByIds(ids);
    }

    @Operation(summary = "按类型id获取流程列表")
    @GetMapping("/names")
    public List<SelectVO> getFlowNameByTypeId(@Parameter(description = "类型id", required = true) Long typeId) {
        return flowService.getFlowNameByTypeId(typeId);
    }

}
