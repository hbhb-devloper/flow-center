package com.hbhb.cw.flowcenter.web.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaokang
 * @since 2020-07-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowVfdVO implements Serializable {
    private static final long serialVersionUID = 1422505442848139368L;

    private Long flowId;
    private String name;
    private List<FlowNodeVO> nodeList;
    private List<FlowLineVO> lineList;
}
