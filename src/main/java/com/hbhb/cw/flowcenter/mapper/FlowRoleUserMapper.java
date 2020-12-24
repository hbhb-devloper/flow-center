package com.hbhb.cw.flowcenter.mapper;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.flowcenter.model.FlowRoleUser;
import com.hbhb.cw.flowcenter.web.vo.FlowRoleUserReqVO;
import com.hbhb.cw.flowcenter.web.vo.FlowRoleUserVO;

import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.mapper.annotation.Param;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface FlowRoleUserMapper extends BaseMapper<FlowRoleUser> {

    PageResult<FlowRoleUserVO> selectPageByCond(@Param("cond") FlowRoleUserReqVO cond,
                                                PageRequest request);

    List<SelectVO> selectUserByRoleInUnit(@Param("list") List<Integer> unitIds,
                                          @Param("flowRoleId") Long flowRoleId);
}
