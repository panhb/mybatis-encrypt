package com.hengheng.entity;

import com.hengheng.jackson.desensitization.Desensitization;
import com.hengheng.mybatis.encrypt.intercept.EncryptData;
import lombok.Data;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2021-11-23 10:47:50
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -67007949206954154L;
    
    private Long pkid;

    @Desensitization
    @EncryptData
    private String phone;

    @EncryptData
    private String idCard;
    
    private String address;
    
    private String name;

}

