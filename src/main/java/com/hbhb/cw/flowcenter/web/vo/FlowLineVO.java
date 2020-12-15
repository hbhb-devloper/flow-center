package com.hbhb.cw.flowcenter.web.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowLineVO implements Serializable {
    private static final long serialVersionUID = 2637922029217855920L;

    private String id;
    private Long flowId;
    private String from;
    private String to;
    private String label;
}