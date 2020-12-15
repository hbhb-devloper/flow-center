package com.hbhb.cw.flowcenter.web.vo;

import org.beetl.sql.annotation.entity.Column;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowNodeVO implements Serializable {
    private static final long serialVersionUID = -5019677791573319064L;

    private String id;

    @Schema(description = "流程id")
    private Long flowId;

    @Schema(description = "节点名称")
    @Column("node_name")
    private String name;

    @Schema(description = "节点类型")
    @Column("node_type")
    private String type;

    @Schema(description = "节点left坐标")
    @Column("p_left")
    private String left;

    @Schema(description = "节点top坐标")
    @Column("p_top")
    private String top;

    @Schema(description = "节点图标")
    private String ico;

    @Schema(description = "节点状态")
    private String state;

    @Schema(description = "节点顺序")
    private Integer sortNum;
}