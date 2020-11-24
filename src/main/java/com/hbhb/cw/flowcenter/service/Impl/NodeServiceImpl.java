package com.hbhb.cw.flowcenter.service.Impl;

import com.hbhb.cw.flowcenter.mapper.NodeMapper;
import com.hbhb.cw.flowcenter.service.NodeService;
import com.hbhb.cw.flowcenter.vo.SelectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxiaogang
 */
@Service
@Slf4j
public class NodeServiceImpl implements NodeService {
    @Resource
    private NodeMapper nodeMapper;

    @Override
    public List<SelectVO> getFlowNodeName(Long flowId) {
        return nodeMapper.selectFlowNodeNameByFlowId(flowId);
    }

    @Override
    public int countFlowNode(Long id) {
        return nodeMapper.countByFlowId(id);
    }
}
