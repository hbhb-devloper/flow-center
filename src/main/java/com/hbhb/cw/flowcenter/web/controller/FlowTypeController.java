package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.api.FlowTypeApi;
import com.hbhb.cw.flowcenter.enums.code.FlowErrorCode;
import com.hbhb.cw.flowcenter.exception.FlowException;
import com.hbhb.cw.flowcenter.model.Flow;
import com.hbhb.cw.flowcenter.model.FlowType;
import com.hbhb.cw.flowcenter.service.FlowService;
import com.hbhb.cw.flowcenter.service.FlowTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.beetl.sql.core.page.PageResult;
import org.springframework.util.CollectionUtils;
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
@Tag(name = "流程类型")
@RestController
@RequestMapping("/type")
public class FlowTypeController implements FlowTypeApi {

    @Resource
    private FlowService flowService;
    @Resource
    private FlowTypeService flowTypeService;

    @Operation(summary = "获取流程类型列表（分页）")
    @GetMapping("/list")
    public PageResult<FlowType> pageFlowType(
            @Parameter(description = "页码，默认为1") @RequestParam(required = false) Integer pageNum,
            @Parameter(description = "每页数量，默认为10") @RequestParam(required = false) Integer pageSize,
            @Parameter(description = "流程类型名称") @RequestParam(required = false) String flowTypeName) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        return flowTypeService.pageFlowType(pageNum, pageSize, flowTypeName);
    }

    @Operation(summary = "按模块获取流程类型列表", description = "下拉框用")
    @GetMapping("/select")
    public List<SelectVO> getFlowTypeList(
            @Parameter(description = "功能模块") @RequestParam(required = false) String module) {
        return flowTypeService.getListByModule(module);
    }

    @Operation(summary = "新增流程类型")
    @PostMapping("")
    public void addFlowType(@Parameter(description = "流程类型实体") @RequestBody FlowType flowType) {
        flowTypeService.upsertFlowType(flowType);
    }

    @Operation(summary = "更新流程类型")
    @PutMapping("")
    public void updateFlowType(@Parameter(description = "流程类型实体") @RequestBody FlowType flowType) {
        flowTypeService.upsertFlowType(flowType);
    }

    @Operation(summary = "删除流程类型")
    @DeleteMapping("/{id}")
    public void deleteFlowType(
            @Parameter(description = "流程类型id") @PathVariable(required = false) Long id) {
        List<Flow> flows = flowService.getFlowsByTypeId(id);
        if (!CollectionUtils.isEmpty(flows)) {
            throw new FlowException(FlowErrorCode.FLOW_TYPE_IS_IN_USE);
        }
        flowTypeService.deleteFlowType(id);
    }

    @Operation(summary = "获取流程类型名称")
    @Override
    public String getNameById(@Parameter(description = "流程类型id") @RequestParam Long id) {
        FlowType flowType = flowTypeService.getFlowTypeInfo(id);
        return flowType == null ? "" : flowType.getFlowTypeName();
    }

    @Override
    public Long getTypeIdByNode(String flowNodeId) {
        return flowTypeService.getIdByNodeId(flowNodeId);
    }

    @Override
    @Operation(summary = "获取流程类型")
    public Map<Long, String> getFlowTypeMapName(List<Long> ids) {
        return flowTypeService.getFlowTypeNameByIds(ids);
    }

}
