package com.hbhb.cw.flowcenter.service;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface RoleUserService {
    /**
     * 通过角色名称获取所有该角色用户
     * @param roleName 角色名称
     * @return 用户id
     */
    List<Integer> getUserId(String roleName);
}
