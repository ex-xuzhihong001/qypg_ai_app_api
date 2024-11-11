package com.rxy.qypg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IndustryTypeEnums {

    INDUSTRY(1,"行业"),
    DEPARTMENT(2,"部门"),
    POSITION(3,"职业"),
            ;

    public static IndustryTypeEnums getEnums(Integer code){
        if(code==null){
            return null;
        }
        for (IndustryTypeEnums value : IndustryTypeEnums.values()) {
            if (value.getType().equals(code)) {
                return value;
            }
        }
        return null;
    }

    private final Integer type;
    private final String name;
}
