package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.core.utils.ExcelUtil;
import com.hbhb.cw.flowcenter.api.FlowRoleUserApi;
import com.hbhb.cw.flowcenter.enums.code.FlowErrorCode;
import com.hbhb.cw.flowcenter.exception.FlowException;
import com.hbhb.cw.flowcenter.service.FlowRoleUserService;
import com.hbhb.cw.flowcenter.web.vo.FlowRoleExportVO;
import com.hbhb.cw.flowcenter.web.vo.FlowRoleUserReqVO;
import com.hbhb.cw.flowcenter.web.vo.FlowRoleUserVO;

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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author wangxiaogang
 */
@Tag(name = "流程角色用户")
@RestController
@RequestMapping("/role/user")
public class FlowRoleUserController implements FlowRoleUserApi {

    @Resource
    private FlowRoleUserService flowRoleUserService;

    @Operation(summary = "（分页）获取流程角色详情")
    @GetMapping("/list")
    public PageResult<FlowRoleUserVO> pageFlowRoleUser(
            @Parameter(description = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
            @Parameter(description = "每页数量，默认为10") @RequestParam(required = false) Integer pageSize,
            @Parameter(description = "流程角色实体") FlowRoleUserReqVO cond) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        return flowRoleUserService.pageFlowRoleUser(cond, pageNum, pageSize);
    }

    @Operation(summary = "获取某单位下拥有该流程角色的用户列表")
    @GetMapping("/select")
    public List<SelectVO> getUserByRoleInUnit(
            @Parameter(description = "单位id") @RequestParam Integer unitId,
            @Parameter(description = "流程角色id") @RequestParam Long flowRoleId) {
        return flowRoleUserService.getUserByRoleInUnit(unitId, flowRoleId);
    }

    @Operation(summary = "更新流程角色用户")
    @PutMapping("/{flowRoleId}")
    public void updateFlowRoleUser(
            @Parameter(description = "流程角色id", required = true) @PathVariable Long flowRoleId,
            @Parameter(description = "用户id列表", required = true) @RequestBody List<Integer> userIds) {
        flowRoleUserService.updateFlowRoleUser(flowRoleId, userIds);
    }

    @Operation(summary = "删除流程角色用户")
    @DeleteMapping("/{id}")
    public void deleteFlowRoleUser(
            @Parameter(description = "流程角色关联id") @PathVariable Long id) {
        flowRoleUserService.deleteFlowRoleUser(id);
    }

    @Operation(summary = "流程角色用户列表导出")
    @PostMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response,
                       @RequestBody @Parameter(description = "流程角色实体") FlowRoleUserReqVO cond) {
        if (cond.getUnitId() == null) {
            throw new FlowException(FlowErrorCode.FLOW_QUERY_LACK_OF_UNIT_ID);
        }
        List<FlowRoleExportVO> list = flowRoleUserService.getExportList(cond);
        String fileName = ExcelUtil.encodingFileName(request, "流程角色列表");
        ExcelUtil.export2Web(response, fileName, fileName, FlowRoleExportVO.class, list);
    }

    @Operation(summary = "按流程角色名称查询用户id")
    @Override
    public List<Integer> getUserIdByRoleId(@Parameter(description = "流程角色id") Long roleId) {
        return flowRoleUserService.getUserIdByRoleId(roleId);
    }

    @Operation(summary = "按流程角色名称查询用户id")
    @Override
    public List<Integer> getUserIdByRoleName(@Parameter(description = "流程角色名称") String roleName) {
        return flowRoleUserService.getUserIdByRoleName(roleName);
    }

    @Operation(summary = "按用户id查询流程角色名称")
    @Override
    public List<String> getRoleNameByUserId(@Parameter(description = "用户id") Integer userId) {
        return flowRoleUserService.getRoleNameByUserId(userId);
    }

    @Operation(summary = "按用户id查询角色id")
    @Override
    public List<Long> getRoleIdByUserId(@Parameter(description = "用户id") Integer userId) {
        return flowRoleUserService.getRoleIdByUserId(userId);
    }

    @Operation(summary = "按用户id和单位id查询角色id")
    @Override
    public List<SelectVO> getUserByRoleAndUnit(Integer unitId, Long flowRoleId) {
        return flowRoleUserService.getUserByRoleInUnit(unitId, flowRoleId);
    }
}
