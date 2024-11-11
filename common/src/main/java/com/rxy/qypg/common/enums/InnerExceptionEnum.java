package com.rxy.qypg.common.enums;

public enum InnerExceptionEnum {
    SYSTEM_ERROR(100001,"系统错误"),

    PARAMS_FAIL(200001,"参数错误"),
    REPEAT_FAIL(300001,"重复操作"),
    NOT_EXIST(400001,"记录不存在"),
    VERIFY_FAIL(500001,"验证失败"),
    OPT_FAIL(600001,"操作失败"),

    ;

    InnerExceptionEnum(long code, String message) {
        this.code = code;
        this.message = message;
    }

    private long code;
    private String message;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
