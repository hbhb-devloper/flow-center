package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.cw.flowcenter.api.FlowNodeApi;
import com.hbhb.cw.flowcenter.service.NodeService;
import com.hbhb.cw.flowcenter.vo.FlowNodePropVO;
import com.hbhb.cw.flowcenter.vo.SelectVO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author wangxiaogang
 */
@Tag(name = "流程相关-节点")
@RestController
@RequestMapping("/flow/node")
public class FlowNodeController implements FlowNodeApi {
    @Resource
    private NodeService nodeService;

    @Override
    public List<SelectVO> getFlowNodeName(Long flowId) {
        return nodeService.getFlowNodeName(flowId);
    }

    @Override
    public int countFlowNode(Long flowId) {
        return nodeService.countFlowNode(flowId);
    }

    @Override
    public List<FlowNodePropVO> getFlowProp(Long flowId) {
        return nodeService.getFlowProp(flowId);
    }
}
