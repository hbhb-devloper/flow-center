package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.cw.flowcenter.api.FlowApi;
import com.hbhb.cw.flowcenter.model.Flow;
import com.hbhb.cw.flowcenter.service.FlowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangxiaogang
 */
@Tag(name = "流程维护")
@RestController
@RequestMapping("/flow")
public class FlowController implements FlowApi {

    @Resource
    private FlowService flowService;

    @Override
    public Flow getFlow(Long flowId) {
        return flowService.getFlow(flowId);
    }

    @Override
    public String getNameByNodeId(String nodeId) {
        return flowService.getNameByNodeId(nodeId);
    }
}
