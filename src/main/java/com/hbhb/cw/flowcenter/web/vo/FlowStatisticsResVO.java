package com.hbhb.cw.flowcenter.web.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowStatisticsResVO implements Serializable {
    private static final long serialVersionUID = 2881361562419549518L;

    @Schema(description = "单位id")
    @JsonIgnore
    private Integer unitId;
    @Schema(description = "单位名称")
    private String unitName;
    @Schema(description = "流程编号")
    private Long flowId;
    @Schema(description = "流程名称")
    private String flowName;
    @Schema(description = "节点id")
    private String flowNodeId;
    @Schema(description = "节点名称")
    private String flowNodeName;
    @Schema(description = "环节名称")
    private String linkName;
    @Schema(description = "角色描述")
    private String roleDescription;
    @Schema(description = "默认用户的id")
    @JsonIgnore
    private Integer userId;
    @Schema(description = "默认用户")
    private String userName;
    @Schema(description = "角色范围id")
    @JsonIgnore
    private Integer roleRangeId;
    @Schema(description = "角色范围")
    private String roleRange;
    @Schema(description = "分配者")
    private String roleLid;
    @Schema(description = "过滤条件")
    private String enableCond;
}
