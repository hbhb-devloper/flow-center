package com.hbhb.cw.flowcenter.web.controller;

import com.hbhb.cw.flowcenter.api.FlowRoleUserApi;
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
@RequestMapping("/flow/user")
public class FlowRoleUserController implements FlowRoleUserApi {
    @Resource
    private RoleUserService roleUserService;

    @Override
    public List<Integer> getFlowRoleUserList(String roleName) {
        return roleUserService.getUserId(roleName);
    }

    @Override
    public List<Long> getFlowRoleIdByUserId(Integer userId) {
        return roleUserService.getFlowRoleIdByUserId(userId);
    }
}
