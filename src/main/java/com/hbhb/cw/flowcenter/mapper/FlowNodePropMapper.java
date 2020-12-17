package com.hbhb.cw.flowcenter.mapper;

import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.model.FlowNodeProp;
import com.hbhb.cw.flowcenter.vo.FlowNodePropVO;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface FlowNodePropMapper extends BaseMapper<FlowNodeProp> {

    List<FlowNodePropVO> selectNodePropList(Long flowId);

    List<SelectVO> selectAssignerList(Long flowId);
}
