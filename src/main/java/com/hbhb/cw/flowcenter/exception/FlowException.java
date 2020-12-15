package com.hbhb.cw.flowcenter.exception;

import com.hbhb.core.bean.MessageConvert;
import com.hbhb.cw.flowcenter.enums.code.FlowErrorCode;
import com.hbhb.web.exception.BusinessException;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public class FlowException extends BusinessException {
    private static final long serialVersionUID = 1435320453347201063L;

    private final String code;

    public FlowException(FlowErrorCode errorCode) {
        super(errorCode.getCode(), MessageConvert.convert(errorCode.getMessage()));
        this.code = errorCode.getCode();
    }
}
