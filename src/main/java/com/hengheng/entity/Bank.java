package com.hengheng.entity;

import com.hengheng.jackson.desensitization.Desensitization;
import com.hengheng.jackson.desensitization.DesensitizationType;
import lombok.Data;

import java.io.Serializable;

/**
 * (Bank)实体类
 *
 * @author makejava
 * @since 2021-11-23 10:47:48
 */
@Data
public class Bank implements Serializable {
    private static final long serialVersionUID = -97084415179455707L;
    
    private Long pkid;

    @Desensitization(type = DesensitizationType.BANK_CARD)
    private String bankCard;
    
    private String bankName;

}

