package com.hbhb.cw.flowcenter.mapper;

import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.flowcenter.model.Flow;
import org.beetl.sql.mapper.annotation.Param;

/**
 * @author wangxiaogang
 */
public interface FlowMapper extends BaseMapper<Flow> {

    String selectNameByNodeId(@Param("flowNodeId") String flowNodeId);
}
