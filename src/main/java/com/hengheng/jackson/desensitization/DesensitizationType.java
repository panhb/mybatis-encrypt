package com.hengheng.jackson.desensitization;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hongbo.pan
 * @date 2021/11/23
 */
@Getter
@AllArgsConstructor
public enum DesensitizationType {

    PHONE(3, 4),
    ID_CARD(6, 4),
    BANK_CARD(6, 4),
    NONE(0,0),
    ;

    /**
     * 保留前面字符的位数
     */
    private int front;

    /**
     * 保留后面字符的位数
     */
    private int end;

}
