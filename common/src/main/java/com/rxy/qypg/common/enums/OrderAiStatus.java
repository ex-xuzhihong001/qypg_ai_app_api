package com.rxy.qypg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderAiStatus {
    INIT(0,"评估报告待执行"),
    ING(1,"评估报告生成中"),
    SUCCESS(2,"评估报告生成成功"),
    FAIL(3,"评估报告生成失败"),
    ;



    private final int code;
    private final String type;
}
