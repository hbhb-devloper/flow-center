package com.hbhb.cw.flowcenter.mapper;

import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.flowcenter.model.Flow;
import com.hbhb.cw.flowcenter.web.vo.FlowResVO;
import com.hbhb.cw.flowcenter.web.vo.FlowStatisticsResVO;

import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;

/**
 * @author wangxiaogang
 */
public interface FlowMapper extends BaseMapper<Flow> {

    PageResult<FlowResVO> selectPageByCond(String flowName, Long flowTypeId, PageRequest request);

    PageResult<FlowStatisticsResVO> selectInfoPageByCond(Integer unitId, Long flowId, PageRequest request);

    String selectNameByNodeId(String nodeId);
}
