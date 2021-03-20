package com.hbhb.cw.flowcenter.web.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaokang
 * @since 2020-07-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowVO implements Serializable {

    private static final long serialVersionUID = 7238772405932524381L;

    @Schema(description = "流程id")
    private Long id;

    @Schema(description = "流程类型id")
    private Long flowTypeId;

    @Schema(description = "流程名称")
    private String flowName;

    @Schema(description = "显示顺序")
    private Integer sortNum;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "关联单位id")
    List<Integer> unitIds;
}
