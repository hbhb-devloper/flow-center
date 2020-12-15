package com.hbhb.cw.flowcenter.service;

import com.hbhb.cw.flowcenter.model.FlowRole;
import com.hbhb.cw.flowcenter.vo.FlowRoleVO;

import org.beetl.sql.core.page.PageResult;

import java.util.List;

/**
 * @author yzc
 */
public interface FlowRoleService {

    /**
     * 分页获取角色列表
     */
    PageResult<FlowRole> pageFlowRole(Integer pageNum, Integer pageSize);

    /**
     * 获取所有角色列表
     */
    List<FlowRoleVO> getAllFlowRoleList();

    /**
     * 新增流程角色
     */
    void upsertFlowRole(FlowRoleVO vo);

    /**
     * 通过id删除流程角色
     */
    void deleteFlowRole(Long id);

//    /**
//     * 通过角色得到该角色参与的流程
//     */
//    List<SelectVO> getFlowByFlowRoleId(Long flowRoleId);

//    /**
//     * 通过角色id获取角色名称
//     */
//    List<String> getFlowRoleName(List<Long> roleIds);

}
