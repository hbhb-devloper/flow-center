package com.hbhb.cw.flowcenter.web.controller;


import com.hbhb.cw.flowcenter.api.FlowApi;
import com.hbhb.cw.flowcenter.service.RoleUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxiaogang
 */
@Tag(name = "流程相关-用户角色")
@RestController
@RequestMapping("/flow-role")
public class FlowRoleController implements FlowApi {

    @Resource
    private RoleUserService roleUserService;


    @Override
    public List<Integer> getFlowRoleUserList(String s) {
        return roleUserService.getUserId(s);
    }
}
