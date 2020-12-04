package com.hbhb.cw.flowcenter.mapper;

import com.hbhb.cw.flowcenter.model.FlowNode;
import com.hbhb.cw.flowcenter.vo.FlowNodePropVO;
import com.hbhb.cw.flowcenter.vo.SelectVO;
import com.hbhb.web.beetlsql.BaseMapper;

import org.beetl.sql.mapper.annotation.Param;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface NodeMapper extends BaseMapper<FlowNode> {
    List<SelectVO> selectFlowNodeNameByFlowId(Long flowId);

    int countByFlowId(@Param("flowId") Long flowId);

    List<FlowNodePropVO> selectFlowNodePropById(Long flowId);
}
