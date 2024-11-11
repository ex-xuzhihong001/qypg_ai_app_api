package com.rxy.qypg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConditionEnums {

    GT("gt","大于"),
    GTE("gte","大于等于"),
    LT("lt","小于"),
    LTE("lte","小于等于"),
            ;


    public static boolean calculate(Integer userScore,Integer triggerScore,String type){
        if(userScore==null || triggerScore==null || type==null){
            return false;
        }
        for (ConditionEnums value : ConditionEnums.values()) {
            if (value.getType().equals(type)) {
                switch (value){
                    case GT: return userScore>triggerScore;
                    case GTE: return userScore>=triggerScore;
                    case LT: return userScore<triggerScore;
                    case LTE: return userScore<=triggerScore;
                    default: return false;
                }
            }
        }
        return false;
    }

    private final String type;
    private final String name;
}
