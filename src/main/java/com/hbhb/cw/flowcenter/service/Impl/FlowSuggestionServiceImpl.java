package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.core.bean.BeanConverter;
import com.hbhb.cw.flowcenter.mapper.FlowSuggestionMapper;
import com.hbhb.cw.flowcenter.model.FlowSuggestion;
import com.hbhb.cw.flowcenter.service.FlowSuggestionService;
import com.hbhb.cw.flowcenter.web.vo.FlowSuggestionVO;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlowSuggestionServiceImpl implements FlowSuggestionService {

    @Resource
    private FlowSuggestionMapper flowSuggestionMapper;

    @Override
    public List<FlowSuggestion> getSuggestionList() {
        return flowSuggestionMapper.createLambdaQuery()
                .asc(FlowSuggestion::getOrderNumber)
                .select();
    }

    @Override
    public FlowSuggestion getSuggestionInfo(Long id) {
        return flowSuggestionMapper.single(id);
    }

    @Override
    public void upsertSuggestion(FlowSuggestionVO vo) {
        flowSuggestionMapper.upsertByTemplate(BeanConverter.convert(vo, FlowSuggestion.class));
    }

    @Override
    public void deleteSuggestion(Long id) {
        flowSuggestionMapper.deleteById(id);
    }
}
