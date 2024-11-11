package com.rxy.qypg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnums {

    USE_ING(1,"使用中"),
    NO_USE(0,"未使用"),

            ;


    private final Integer status;
    private final String desc;
}
