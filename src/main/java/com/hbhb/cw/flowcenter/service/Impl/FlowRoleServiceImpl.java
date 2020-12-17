package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.core.bean.BeanConverter;
import com.hbhb.core.bean.SelectVO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<SelectVO> getAllFlowRoleList() {
        List<FlowRole> list = flowRoleMapper.createLambdaQuery()
                .asc(FlowRole::getSortNum)
                .select();
        return Optional.ofNullable(list)
                .orElse(new ArrayList<>())
                .stream()
                .map(flowRole -> SelectVO.builder()
                        .id(flowRole.getId())
                        .label(flowRole.getRoleName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void upsertFlowRole(FlowRoleVO vo) {
        flowRoleMapper.upsertByTemplate(BeanConverter.convert(vo, FlowRole.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFlowRole(Long id) {
        // 判断该流程角色是否被使用
        // 1.是否在角色用户关联表中使用
        long count1 = flowRoleUserMapper.createLambdaQuery()
                .andEq(FlowRoleUser::getFlowRoleId, id)
                .count();
        if (count1 > 0) {
            throw new FlowException(FlowErrorCode.FLOW_ROLE_IS_IN_USE);
        }
        // 2.是否在节点属性表中使用
        FlowRole flowRole = flowRoleMapper.single(id);
        long count2 = flowNodePropMapper.createLambdaQuery()
                .andEq(FlowNodeProp::getFlowRoleId, id)
                .andEq(FlowNodeProp::getRoleDesc, flowRole.getRoleName())
                .count();
        if (count2 > 0) {
            throw new FlowException(FlowErrorCode.FLOW_ROLE_IS_IN_USE);
        }
        // 删除主表
        flowRoleMapper.deleteById(id);
        // 删除关联表
        flowRoleUserMapper.createLambdaQuery()
                .andEq(FlowRoleUser::getFlowRoleId, id)
                .delete();
    }
}
