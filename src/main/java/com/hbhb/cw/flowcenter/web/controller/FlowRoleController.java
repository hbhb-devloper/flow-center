package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.api.FlowRoleApi;
import com.hbhb.cw.flowcenter.model.FlowRole;
import com.hbhb.cw.flowcenter.service.FlowRoleService;
import com.hbhb.cw.flowcenter.vo.FlowRoleVO;

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

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author yzc
 */
@Tag(name = "流程角色")
@RestController
@RequestMapping("/role")
public class FlowRoleController implements FlowRoleApi {

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
    public void deleteFlowRole(@Parameter(description = "流程角色id") @PathVariable Long id) {
        flowRoleService.deleteFlowRole(id);
    }

    @Operation(summary = "查询流程角色名称")
    @Override
    public String getNameById(@Parameter(description = "流程角色id") @RequestParam Long id) {
        FlowRole flowRole = flowRoleService.getFlowRole(id);
        return Optional.ofNullable(flowRole.getRoleName()).orElse("");
    }
}
