package com.hbhb.cw.flowcenter.web.vo;

import org.beetl.sql.annotation.entity.Column;

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

    @Column("from_node_id")
    private String from;

    @Column("to_node_id")
    private String to;

    private String label;
}