package com.hbhb.cw.flowcenter.mapper;

import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.flowcenter.model.FlowNode;
import com.hbhb.cw.flowcenter.web.vo.FlowNodeResVO;

import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface FlowNodeMapper extends BaseMapper<FlowNode> {

    PageResult<FlowNodeResVO> selectPageByCond(Integer unitId, Long flowId, PageRequest request);

    void insertBatch(List<FlowNode> list);
}
