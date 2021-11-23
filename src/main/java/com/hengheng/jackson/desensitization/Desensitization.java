package com.hengheng.jackson.desensitization;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hongbo.pan
 * @date 2021/11/23
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizationSerializer.class)
public @interface Desensitization {

    /**
     * 保留前面字符的位数
     */
    int front() default 3;

    /**
     * 保留后面字符的位数
     */
    int end() default 4;

    /**
     * 脱敏类型，不为NONE则front和end失效
     * @return
     */
    DesensitizationType type() default DesensitizationType.NONE;
}
