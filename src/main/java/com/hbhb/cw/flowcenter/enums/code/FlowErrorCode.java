package com.hbhb.cw.flowcenter.enums.code;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public enum FlowErrorCode {

    FLOW_UNIT_NULL_ERROR("FL001", "flow.unit.null.error"),
    FLOW_IS_IN_USE("FL002", "flow.is.in.use"),
    FLOW_TYPE_IS_IN_USE("FL003", "flow.type.is.in.use"),
    FLOW_ROLE_IS_IN_USE("FL004", "flow.role.is.in.use"),
    FLOW_QUERY_LACK_OF_UNIT_ID("FL005", "flow.query.lack.of.unit_id"),

    ;

    private final String code;

    private final String message;

    FlowErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
