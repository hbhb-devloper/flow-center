package com.hbhb.cw.flowcenter.mapper;

import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.flowcenter.model.FlowNode;
import com.hbhb.cw.flowcenter.web.vo.FlowNodeResVO;
import com.hbhb.cw.flowcenter.web.vo.FlowNodeVO;

import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.mapper.annotation.Param;
import org.beetl.sql.mapper.annotation.Update;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface FlowNodeMapper extends BaseMapper<FlowNode> {

    PageResult<FlowNodeResVO> selectPageByCond(Integer unitId, Long flowId, PageRequest request);

    @Update
    void batchInsert(@Param("list") List<FlowNodeVO> list);
}
