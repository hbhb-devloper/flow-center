package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.core.bean.BeanConverter;
import com.hbhb.cw.flowcenter.mapper.FlowNodeNoticeMapper;
import com.hbhb.cw.flowcenter.model.FlowNodeNotice;
import com.hbhb.cw.flowcenter.service.FlowNodeNoticeService;
import com.hbhb.cw.flowcenter.vo.FlowNodeNoticeVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangxiaogang
 */
@Service
@Slf4j
public class FlowNodeNoticeServiceImpl implements FlowNodeNoticeService {
    @Resource
    private FlowNodeNoticeMapper flowNodeNoticeMapper;

    @Override
    public List<FlowNodeNoticeVO> getNodeNoticeList(String flowNodeId) {
        List<FlowNodeNotice> list = flowNodeNoticeMapper.createLambdaQuery()
                .andEq(FlowNodeNotice::getFlowNodeId, flowNodeId)
                .select();
        return BeanConverter.copyBeanList(list, FlowNodeNoticeVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addNodeNotice(List<FlowNodeNoticeVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        // 先删除
        flowNodeNoticeMapper.createLambdaQuery()
                .andEq(FlowNodeNotice::getFlowNodeId, list.get(0).getFlowNodeId())
                .delete();
        // 再新增
        List<FlowNodeNotice> insertList = BeanConverter.copyBeanList(list, FlowNodeNotice.class);
        flowNodeNoticeMapper.insertBatch(insertList);
    }

    @Override
    public void updateNodeNotice(FlowNodeNoticeVO vo) {
        flowNodeNoticeMapper.updateTemplateById(BeanConverter.convert(vo, FlowNodeNotice.class));
    }

    @Override
    public void deleteNodeNotice(Long id) {
        flowNodeNoticeMapper.deleteById(id);
    }
}
