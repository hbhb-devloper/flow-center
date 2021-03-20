package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.core.bean.BeanConverter;
import com.hbhb.cw.flowcenter.mapper.FlowNoticeMapper;
import com.hbhb.cw.flowcenter.model.FlowNotice;
import com.hbhb.cw.flowcenter.service.FlowNoticeService;
import com.hbhb.cw.flowcenter.web.vo.FlowNoticeVO;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlowNoticeServiceImpl implements FlowNoticeService {

    @Resource
    private FlowNoticeMapper flowNoticeMapper;

    @Override
    @SuppressWarnings(value = {"unchecked"})
    public List<FlowNoticeVO> getNoticeList() {
        List<FlowNotice> list = flowNoticeMapper.createLambdaQuery()
                .select(FlowNotice::getId, FlowNotice::getContent, FlowNotice::getRemark);
        return BeanConverter.copyBeanList(list, FlowNoticeVO.class);
    }

    @Override
    public FlowNoticeVO getNoticeInfo(Long id) {
        FlowNotice flowNotice = flowNoticeMapper.single(id);
        return BeanConverter.convert(flowNotice, FlowNoticeVO.class);
    }

    @Override
    public void upsertNotice(FlowNoticeVO vo) {
        flowNoticeMapper.upsertByTemplate(BeanConverter.convert(vo, FlowNotice.class));
    }

    @Override
    public void deleteNotice(Long id) {
        flowNoticeMapper.deleteById(id);
    }
}
