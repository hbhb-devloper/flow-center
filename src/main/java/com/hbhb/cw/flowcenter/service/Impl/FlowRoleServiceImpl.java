package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.core.bean.BeanConverter;
import com.hbhb.cw.flowcenter.enums.code.FlowErrorCode;
import com.hbhb.cw.flowcenter.exception.FlowException;
import com.hbhb.cw.flowcenter.mapper.FlowNodePropMapper;
import com.hbhb.cw.flowcenter.mapper.FlowRoleMapper;
import com.hbhb.cw.flowcenter.mapper.FlowRoleUserMapper;
import com.hbhb.cw.flowcenter.model.FlowNodeProp;
import com.hbhb.cw.flowcenter.model.FlowRole;
import com.hbhb.cw.flowcenter.model.FlowRoleUser;
import com.hbhb.cw.flowcenter.service.FlowRoleService;
import com.hbhb.cw.flowcenter.vo.FlowRoleVO;

import org.beetl.sql.core.page.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

/**
 * @author yzc
 */
@Service
public class FlowRoleServiceImpl implements FlowRoleService {

    @Resource
    private FlowRoleMapper flowRoleMapper;
    @Resource
    private FlowNodePropMapper flowNodePropMapper;
    @Resource
    private FlowRoleUserMapper flowRoleUserMapper;

    @Override
    public PageResult<FlowRole> pageFlowRole(Integer pageNum, Integer pageSize) {
        return flowRoleMapper.createLambdaQuery()
                .asc(FlowRole::getSortNum)
                .page(pageNum, pageSize);
    }

    @Override
    public List<FlowRoleVO> getAllFlowRoleList() {
        List<FlowRole> list = flowRoleMapper.createLambdaQuery()
                .asc(FlowRole::getSortNum)
                .select();
        return BeanConverter.copyBeanList(list, FlowRoleVO.class);
    }

    @Override
    public void upsertFlowRole(FlowRoleVO vo) {
        flowRoleMapper.upsertByTemplate(BeanConverter.convert(vo, FlowRole.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFlowRole(Long id) {
        // 判断该角色在流程节点属性表中是否被使用
        long count = flowNodePropMapper.createLambdaQuery()
                .andEq(FlowNodeProp::getFlowRoleId, id)
                .count();
        if (count > 0) {
            throw new FlowException(FlowErrorCode.FLOW_ROLE_IS_IN_USE);
        }
        // 删除主表
        flowRoleMapper.deleteById(id);
        // 删除关联表
        flowRoleUserMapper.createLambdaQuery()
                .andEq(FlowRoleUser::getFlowRoleId, id)
                .delete();
    }

//    @Override
//    public List<SelectVO> getFlowByFlowRoleId(Long flowRoleId) {
//        return flowRoleMapper.selectFlowByFlowRoleId(flowRoleId);
//    }

//    @Override
//    public List<String> getFlowRoleName(List<Long> roleIds) {
//        return flowRoleMapper.selectRoleNameByRoleId(roleIds);
//    }
}
