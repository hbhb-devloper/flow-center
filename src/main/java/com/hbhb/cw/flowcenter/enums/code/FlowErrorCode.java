package com.hbhb.cw.flowcenter.enums.code;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public enum FlowErrorCode {

    FLOW_UNIT_NULL_ERROR("F0001", "flow.unit.null.error"),
    FLOW_IS_IN_USE("F0002", "flow.is.in.use"),
    FLOW_TYPE_IS_IN_USE("F0003", "flow.type.is.in.use"),
    FLOW_ROLE_IS_IN_USE("F0004", "flow.role.is.in.use"),

    ;

    private final String code;

    private final String message;

    FlowErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
