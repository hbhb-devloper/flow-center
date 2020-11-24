package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.cw.flowcenter.api.FlowTypeApi;
import com.hbhb.cw.flowcenter.service.TypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
