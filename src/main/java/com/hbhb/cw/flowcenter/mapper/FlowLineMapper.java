package com.hbhb.cw.flowcenter.mapper;

import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.flowcenter.model.FlowLine;
import com.hbhb.cw.flowcenter.web.vo.FlowLineVO;

import org.beetl.sql.mapper.annotation.Param;
import org.beetl.sql.mapper.annotation.Update;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface FlowLineMapper extends BaseMapper<FlowLine> {

    @Update
    void batchInsert(@Param("list") List<FlowLineVO> list);
}
