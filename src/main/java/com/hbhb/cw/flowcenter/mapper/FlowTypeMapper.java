package com.hbhb.cw.flowcenter.mapper;

import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.flowcenter.model.FlowType;

/**
 * @author wangxiaogang
 */
public interface FlowTypeMapper extends BaseMapper<FlowType> {

    Long selectIdByNodeId(String flowNodeId);

}
