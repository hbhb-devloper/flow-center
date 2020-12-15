package com.hbhb.cw.flowcenter.web.vo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (c) 2020 Choice, Inc. All Rights Reserved. Choice Proprietary and Confidential.
 *
 * @author jiyu@myweimai.com
 * @since 2020-12-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowRoleUserVO implements Serializable {
    private static final long serialVersionUID = -3214900153153515821L;

    private Long id;
    @Schema(description = "单位名称")
    private String unitName;
    @Schema(description = "流程角色名称")
    private String roleName;
    @Schema(description = "用户名称")
    private String nickName;
}
