package com.hbhb.cw.flowcenter.service;

import com.hbhb.api.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.model.FlowLine;
import com.hbhb.cw.flowcenter.web.vo.FlowExportVO;
import com.hbhb.cw.flowcenter.web.vo.FlowNodeResVO;
import org.beetl.sql.core.page.PageResult;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface FlowNodeService {

    /**
     * 分页查询流程节点列表
     */
    PageResult<FlowNodeResVO> pageFlowNode(Integer pageNum, Integer pageSize,
                                           Integer unitId, Long flowId);

    /**
     * 删除节点
     */
    void deleteFlowNode(String id);

    /**
     * 根据(开始节点id，结束节点id)获取节点链接线
     */
    FlowLine getNodeLine(String fromNodeId, String toNodeId);

    /**
     * 删除节点链接线
     */
    void deleteNodeLine(String id);

    /**
     * 跟据流程id获取流程节点信息
     */
    List<SelectVO> getNodeMap(Long flowId);

    /**
     * 导出节点列表
     */
    List<FlowExportVO> getExportList(Integer unitId, Long flowId);

    /**
     * 获取流程的节点数
     */
    long getNodeNum(Long id);
}
