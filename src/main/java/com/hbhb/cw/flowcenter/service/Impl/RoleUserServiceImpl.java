package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.cw.flowcenter.mapper.RoleUserMapper;
import com.hbhb.cw.flowcenter.service.RoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxiaogang
 */
@Service
@Slf4j
public class RoleUserServiceImpl implements RoleUserService {
    @Resource
    private RoleUserMapper roleUserMapper;

    @Override
    public List<Integer> getUserId(String roleName) {
        return roleUserMapper.selectRoleUserIdByRoleName(roleName);
    }
}
