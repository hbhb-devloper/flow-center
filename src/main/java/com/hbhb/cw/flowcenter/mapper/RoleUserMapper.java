package com.hbhb.cw.flowcenter.mapper;


import com.hbhb.cw.flowcenter.model.FlowRoleUser;
import com.hbhb.web.beetlsql.BaseMapper;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface RoleUserMapper extends BaseMapper<FlowRoleUser> {

    List<Integer> selectRoleUserIdByRoleName(String roleName);
}
