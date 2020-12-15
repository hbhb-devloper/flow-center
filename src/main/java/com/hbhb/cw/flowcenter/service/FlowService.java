package com.hbhb.cw.flowcenter.service;

import com.hbhb.core.bean.SelectVO;
import com.hbhb.cw.flowcenter.model.Flow;
import com.hbhb.cw.flowcenter.web.vo.FlowExportVO;
import com.hbhb.cw.flowcenter.web.vo.FlowProjectVO;
import com.hbhb.cw.flowcenter.web.vo.FlowResVO;
import com.hbhb.cw.flowcenter.web.vo.FlowStatisticsResVO;
import com.hbhb.cw.flowcenter.web.vo.FlowVO;

import org.beetl.sql.core.page.PageResult;

import java.util.List;

/**
 * @author wangxiaogang
 */
public interface FlowService {

    /**
     * 分页查询流程列表
     */
    PageResult<FlowResVO> pageFlow(Integer pageNum, Integer pageSize,
                                   String flowName, Long flowTypeId);

    /**
     * 分页查询流程详情列表
     */
    PageResult<FlowStatisticsResVO> pageFlowInfo(Integer pageNum, Integer pageSize,
                                                 Integer unitId, Long flowId);

    /**
     * 获取流程详情
     */
    FlowVO getFlowInfo(Long flowId);

    /**
     * 新增流程
     */
    void addFlow(FlowVO vo);

    /**
     * 更新流程
     */
    void updateFlow(FlowVO vo);

    /**
     * （逻辑）删除流程
     */
    void deleteFlow(Long flowId);

    /**
     * （物理）删除流程
     */
    void removeFlow(Long flowId);

    /**
     * 获取流程项目详情
     */
    FlowProjectVO getFlowProjectInfo(Long flowId);

    /**
     * 保存流程项目详情
     */
    void saveFlowProjectInfo(FlowProjectVO vo);

    /**
     * 获取所有流程名称列表
     */
    List<SelectVO> getFlowNameList();

    /**
     * 根据id导出节点属性列表
     */
    List<FlowExportVO> getExportList(Integer unitId, Long flowId);

    /**
     * 按id获取流程详情
     */
    Flow getFlowById(Long flowId);

    /**
     * 按节点id得到流程名称
     */
    String getNameByNodeId(String nodeId);

    /**
     * 按类型id获取流程列表
     */
    List<Flow> getFlowsByTypeId(Long typeId);
}
