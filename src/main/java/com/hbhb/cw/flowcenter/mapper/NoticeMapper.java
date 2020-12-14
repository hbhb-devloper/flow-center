package com.hbhb.cw.flowcenter.mapper;

import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.flowcenter.model.FlowNodeNotice;
import com.hbhb.cw.flowcenter.vo.FlowNodeNoticeResVO;
import org.beetl.sql.mapper.annotation.Param;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface NoticeMapper extends BaseMapper<FlowNodeNotice> {
    List<FlowNodeNoticeResVO> selectNoticeByFlowNodeId(@Param("flowNodeId") String flowNodeId);
}