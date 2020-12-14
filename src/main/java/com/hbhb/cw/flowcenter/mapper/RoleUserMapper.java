package com.hbhb.cw.flowcenter.mapper;


import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.flowcenter.model.FlowRoleUser;
import org.beetl.sql.mapper.annotation.Param;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface RoleUserMapper extends BaseMapper<FlowRoleUser> {

    List<Integer> selectRoleUserIdByRoleName(String roleName);

    List<Long> selectFlowRoleByUserId(@Param("userId") Integer userId);

}
