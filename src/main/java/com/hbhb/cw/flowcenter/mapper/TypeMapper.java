package com.hbhb.cw.flowcenter.mapper;

import com.hbhb.cw.flowcenter.model.FlowType;
import com.hbhb.web.beetlsql.BaseMapper;
import org.beetl.sql.mapper.annotation.Param;

/**
 * @author wangxiaogang
 */
public interface TypeMapper extends BaseMapper<FlowType> {
    Long selectIdByNodeId(@Param("flowNodeId") String flowNodeId);
}
