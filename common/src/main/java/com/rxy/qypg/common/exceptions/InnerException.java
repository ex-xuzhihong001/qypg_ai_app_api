package com.rxy.qypg.common.exceptions;

import com.rxy.qypg.common.enums.InnerExceptionEnum;

import java.io.Serializable;


public class InnerException extends RuntimeException implements Serializable {

    private long code;

    public InnerException(long code, String message) {
        super(message);
        this.code = code;
    }

    public InnerException(InnerExceptionEnum ce) {
        super(ce.getMessage());
        this.code = ce.getCode();
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}
