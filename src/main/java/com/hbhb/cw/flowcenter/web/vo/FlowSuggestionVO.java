package com.hbhb.cw.flowcenter.web.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowSuggestionVO implements Serializable {
    private static final long serialVersionUID = 7401442137113540385L;

    private Long id;

    @Schema(description = "审批意见")
    private String suggestion;

    @Schema(description = "显示顺序")
    private Integer orderNumber;
}
