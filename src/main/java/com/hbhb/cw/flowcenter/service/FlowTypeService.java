package com.hbhb.cw.flowcenter.service;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.model.FlowType;
import org.beetl.sql.core.page.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author wangxiaogang
 */
public interface FlowTypeService {

    /**
     * 分页获取流程类型列表
     */
    PageResult<FlowType> pageFlowType(Integer pageNum, Integer pageSize, String flowTypeName);

    /**
     * 按模块获流程类型列表
     */
    List<SelectVO> getListByModule(String module);

    /**
     * 获取流程类型详情
     */
    FlowType getFlowTypeInfo(Long id);

    /**
     * 新增/修改流程类型
     */
    void upsertFlowType(FlowType flowType);

    /**
     * 删除流程类型
     */
    void deleteFlowType(Long id);

    /**
     * 跟据节点id获取流程类型id
     */
    Long getIdByNodeId(String flowNodeId);

    /**
     * 跟据id批量获取名称
     *
     * @param ids ids
     * @return 列表
     */
    Map<Long, String> getFlowTypeNameByIds(List<Long> ids);
}
