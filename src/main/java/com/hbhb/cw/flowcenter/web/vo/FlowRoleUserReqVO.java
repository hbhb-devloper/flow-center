package com.hbhb.cw.flowcenter.web.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yzc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowRoleUserReqVO implements Serializable {
    private static final long serialVersionUID = -8858870036430533311L;

    @Schema(description = "单位id")
    private Integer unitId;
    @Schema(description = "角色id")
    private Long flowRoleId;
    @Schema(description = "用户名称")
    private String nickName;
}
