package com.hbhb.cw.flowcenter.service;

import com.hbhb.cw.flowcenter.web.vo.FlowRoleExportVO;
import com.hbhb.cw.flowcenter.web.vo.FlowRoleUserReqVO;
import com.hbhb.cw.flowcenter.web.vo.FlowRoleUserVO;

import org.beetl.sql.core.page.PageResult;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface FlowRoleUserService {

    /**
     * 分页获取流程角色用户
     */
    PageResult<FlowRoleUserVO> pageFlowRoleUser(FlowRoleUserReqVO cond, Integer pageNum, Integer pageSize);

    /**
     * 更新流程角色用户
     */
    void updateFlowRoleUser(Long flowRoleId, List<Integer> userIds);

    /**
     * 删除流程角色用户
     */
    void deleteFlowRoleUser(Long id);

    /**
     * 导出流程角色用户
     */
    List<FlowRoleExportVO> getExportList(FlowRoleUserReqVO cond);

    /**
     * 按角色名称查询角色用户
     */
    List<Integer> getUserIdByRoleName(String roleName);

    /**
     * 按角色名称查询角色用户
     */
    List<String> getRoleNameByUserId(Integer userId);

    /**
     * 按用户id查询角色id
     */
    List<Long> getRoleIdByUserId(Integer userId);

}
