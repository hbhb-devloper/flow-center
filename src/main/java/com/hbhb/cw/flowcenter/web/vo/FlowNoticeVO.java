package com.hbhb.cw.flowcenter.web.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowNoticeVO implements Serializable {
    private static final long serialVersionUID = 3487507570356529968L;

    private Long id;
    @Schema(description = "提醒内容")
    private String content;
    @Schema(description = "备注")
    private String remark;
}

