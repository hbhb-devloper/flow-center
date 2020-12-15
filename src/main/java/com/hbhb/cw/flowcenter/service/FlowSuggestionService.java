package com.hbhb.cw.flowcenter.service;

import com.hbhb.cw.flowcenter.model.FlowSuggestion;
import com.hbhb.cw.flowcenter.web.vo.FlowSuggestionVO;

import java.util.List;


public interface FlowSuggestionService {

    /**
     * 查询审批意见列表
     */
    List<FlowSuggestion> getSuggestionList();

    /**
     * 查询审批详情
     */
    FlowSuggestion getSuggestionInfo(Long id);

    /**
     * 新增/修改审批意见
     */
    void upsertSuggestion(FlowSuggestionVO vo);

    /**
     * 删除审批意见
     */
    void deleteSuggestion(Long id);
}
