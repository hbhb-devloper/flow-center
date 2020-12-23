package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.core.bean.BeanConverter;
import com.hbhb.cw.flowcenter.mapper.FlowRoleMapper;
import com.hbhb.cw.flowcenter.mapper.FlowRoleUserMapper;
import com.hbhb.cw.flowcenter.model.FlowRole;
import com.hbhb.cw.flowcenter.model.FlowRoleUser;
import com.hbhb.cw.flowcenter.rpc.UnitApiExp;
import com.hbhb.cw.flowcenter.rpc.UserApiExp;
import com.hbhb.cw.flowcenter.service.FlowRoleUserService;
import com.hbhb.cw.flowcenter.web.vo.FlowRoleExportVO;
import com.hbhb.cw.flowcenter.web.vo.FlowRoleUserReqVO;
import com.hbhb.cw.flowcenter.web.vo.FlowRoleUserVO;
import com.hbhb.cw.systemcenter.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wangxiaogang
 */
@Service
@Slf4j
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class FlowRoleUserServiceImpl implements FlowRoleUserService {

    @Resource
    private FlowRoleMapper flowRoleMapper;
    @Resource
    private FlowRoleUserMapper flowRoleUserMapper;
    @Resource
    private UnitApiExp unitApi;
    @Resource
    private UserApiExp userApi;

    @Override
    public PageResult<FlowRoleUserVO> pageFlowRoleUser(FlowRoleUserReqVO cond,
                                                       Integer pageNum, Integer pageSize) {
        PageRequest request = DefaultPageRequest.of(pageNum, pageSize);
        return flowRoleUserMapper.selectPageByCond(cond, request);
    }

    @Override
    public List<SelectVO> getUserByRoleInUnit(Integer unitId, Long flowRoleId) {
        List<Integer> unitIds = unitApi.getSubUnit(unitId);
        return flowRoleUserMapper.selectUserByRoleInUnit(unitIds, flowRoleId);
    }

    @Override
    public void updateFlowRoleUser(Long flowRoleId, List<Integer> userIds) {
        List<FlowRoleUser> list = new ArrayList<>();
        userIds.forEach(userId -> {
            long count = flowRoleUserMapper.createLambdaQuery()
                    .andEq(FlowRoleUser::getFlowRoleId, flowRoleId)
                    .andEq(FlowRoleUser::getUserId, userId)
                    .count();
            if (count == 0) {
                list.add(FlowRoleUser.builder()
                        .flowRoleId(flowRoleId)
                        .userId(userId)
                        .build());
            }
        });
        if (list.size() > 0) {
            flowRoleUserMapper.insertBatch(list);
        }
    }

    @Override
    public void deleteFlowRoleUser(Long id) {
        flowRoleUserMapper.deleteById(id);
    }

    @Override
    public List<FlowRoleExportVO> getExportList(FlowRoleUserReqVO cond) {
        PageRequest request = DefaultPageRequest.of(1, Integer.MAX_VALUE);
        PageResult<FlowRoleUserVO> page = flowRoleUserMapper.selectPageByCond(cond, request);
        return Optional.ofNullable(page.getList())
                .orElse(new ArrayList<>())
                .stream()
                .map(vo -> BeanConverter.convert(vo, FlowRoleExportVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SelectVO> getUserIdByRoleName(String roleName) {
        List<FlowRole> flowRoles = flowRoleMapper.createLambdaQuery()
                .andEq(FlowRole::getRoleName, roleName)
                .select(FlowRole::getId);
        if (CollectionUtils.isEmpty(flowRoles)) {
            return new ArrayList<>();
        }

        List<Long> flowRoleIds = flowRoles.stream().map(FlowRole::getId).collect(Collectors.toList());
        List<FlowRoleUser> flowRoleUsers = flowRoleUserMapper.createLambdaQuery()
                .andIn(FlowRoleUser::getFlowRoleId, flowRoleIds)
                .select();
        List<Integer> userIds = flowRoleUsers.stream().map(FlowRoleUser::getUserId).collect(Collectors.toList());
        List<UserInfo> userList = userApi.getUserInfoBatch(userIds);
        return Optional.of(userList)
                .orElse(new ArrayList<>())
                .stream()
                .map(userInfo -> SelectVO.builder()
                        .id(Long.valueOf(userInfo.getId()))
                        .label(userInfo.getNickName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleNameByUserId(Integer userId) {
        List<FlowRoleUser> flowRoleUsers = flowRoleUserMapper.createLambdaQuery()
                .andEq(FlowRoleUser::getUserId, userId)
                .select();
        if (CollectionUtils.isEmpty(flowRoleUsers)) {
            return new ArrayList<>();
        }

        List<Long> flowRoleIds = flowRoleUsers.stream().map(FlowRoleUser::getId).collect(Collectors.toList());
        List<FlowRole> flowRoles = flowRoleMapper.createLambdaQuery()
                .andIn(FlowRole::getId, flowRoleIds)
                .select(FlowRole::getRoleName);
        return flowRoles.stream().map(FlowRole::getRoleName).collect(Collectors.toList());
    }

    @Override
    public List<Long> getRoleIdByUserId(Integer userId) {
        List<FlowRoleUser> list = flowRoleUserMapper.createLambdaQuery()
                .andEq(FlowRoleUser::getUserId, userId)
                .select();
        return Optional.ofNullable(list)
                .orElse(new ArrayList<>())
                .stream()
                .map(FlowRoleUser::getFlowRoleId)
                .collect(Collectors.toList());
    }

}
