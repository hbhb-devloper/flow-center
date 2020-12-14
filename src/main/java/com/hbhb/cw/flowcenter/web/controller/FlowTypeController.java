package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.cw.flowcenter.api.FlowTypeApi;
import com.hbhb.cw.flowcenter.model.Flow;
import com.hbhb.cw.flowcenter.service.TypeService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author wangxiaogang
 */
@Tag(name = "流程相关-用户角色")
@RestController
@RequestMapping("/flow/type")
public class FlowTypeController implements FlowTypeApi {
    @Resource
    private TypeService typeService;

    @Override
    public Long getIdByNodeId(String nodeId) {
        return typeService.getIdByNodeId(nodeId);
    }

    @Override
    public List<Flow> getFlowsByTypeId(Long flowTypeId) {
       return typeService.getFlowsByTypeId(flowTypeId);
    }
}
