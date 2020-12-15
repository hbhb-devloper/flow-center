package com.hbhb.cw.flowcenter.web.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowExportReqVO implements Serializable {

    private static final long serialVersionUID = 3444397887244617768L;

    @Schema(description = "流程id")
    private Long flowId;

    @Schema(description = "单位id")
    private Integer unitId;
}
