package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.model.FlowRole;
import com.hbhb.cw.flowcenter.service.FlowRoleService;
import com.hbhb.cw.flowcenter.vo.FlowRoleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.beetl.sql.core.page.PageResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yzc
 */
@Tag(name = "流程角色")
@RestController
@RequestMapping("/role")
public class FlowRoleController {

    @Resource
    private FlowRoleService flowRoleService;

    @Operation(summary = "（分页）获取角色列表")
    @GetMapping("/list")
    public PageResult<FlowRole> pageFlowRole(
            @Parameter(description = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
            @Parameter(description = "每页数量，默认为10") @RequestParam(required = false) Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        return flowRoleService.pageFlowRole(pageNum, pageSize);
    }

    @Operation(summary = "流程角色库列表")
    @GetMapping("/select")
    public List<SelectVO> getAllFlowRoleList() {
        return flowRoleService.getAllFlowRoleList();
    }

    @Operation(summary = "新增流程角色")
    @PostMapping("")
    public void addFlowRole(@Parameter(description = "流程角色实体") @RequestBody FlowRoleVO vo) {
        flowRoleService.upsertFlowRole(vo);
    }

    @Operation(summary = "更新流程角色")
    @PutMapping("")
    public void updateFlowRole(@Parameter(description = "流程角色实体") @RequestBody FlowRoleVO vo) {
        flowRoleService.upsertFlowRole(vo);
    }

    @Operation(summary = "删除流程角色")
    @DeleteMapping("/{id}")
    public void deleteFlowRole(
            @Parameter(description = "流程角色id") @PathVariable(required = false) Long id) {
        flowRoleService.deleteFlowRole(id);
    }
}
