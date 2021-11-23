package com.hengheng.jackson.desensitization;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * @author hongbo.pan
 * @date 2021/11/23
 */
@NoArgsConstructor
@AllArgsConstructor
public class DesensitizationSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    /**
     * 保留前面字符的位数
     */
    private int front;

    /**
     * 保留后面字符的位数
     */
    private int end;

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (ObjectUtil.isNotNull(o)) {
            if (o instanceof String && StrUtil.isNotBlank(o.toString())) {
                jsonGenerator.writeString(DesensitizedUtil.idCardNum(o.toString(), front, end));
            }
        } else {
            jsonGenerator.writeNull();
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (ObjectUtil.isNotNull(beanProperty)) {
            Desensitization desensitization = beanProperty.getAnnotation(Desensitization.class);
            if (ObjectUtil.isNull(desensitization)) {
                desensitization = beanProperty.getContextAnnotation(Desensitization.class);
            }
            if (ObjectUtil.isNotNull(desensitization)) {
                if (DesensitizationType.NONE.equals(desensitization.type())) {
                    return new DesensitizationSerializer(desensitization.front(), desensitization.end());
                } else {
                    return new DesensitizationSerializer(desensitization.type().getFront(), desensitization.type().getEnd());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}
