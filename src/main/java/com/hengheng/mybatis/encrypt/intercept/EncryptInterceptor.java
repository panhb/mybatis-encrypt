package com.hengheng.mybatis.encrypt.intercept;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.hengheng.utils.AesUtils;
import lombok.SneakyThrows;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * 加解密拦截器
 * @author hongbo.pan
 * @date 2021/11/23
 */
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class EncryptInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        Object parameterObject = invocation.getArgs()[1];
        String namespace = statement.getId();
        String className = namespace.substring(0, namespace.lastIndexOf("."));
        String methodName = namespace.substring(namespace.lastIndexOf(".") + 1);

        //反射查找方法，不支持方法重载
        Method[] ms = Class.forName(className).getMethods();
        Method m = Arrays.stream(ms).filter(m1 -> m1.getName().equals(methodName)).findFirst().get();
        m.setAccessible(true);
        Parameter[] parameters = m.getParameters();

        // 多参数
        if (parameterObject instanceof Map) {
            Map parameterMap = (Map)parameterObject;
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Object object = parameterMap.get(parameter.getName());
                if (object instanceof String) {
                    object = AesUtils.encrypt((String)object);
                } else if (object instanceof Collection) {
                    Collection collection = (Collection) object;
                    if (CollectionUtil.isNotEmpty(collection)) {
                        for (Object o : collection) {
                            encryptObject(o);
                        }
                    }
                } else {
                    encryptObject(object);
                }
                parameterMap.put(parameter.getName(), object);
            }
            invocation.getArgs()[1] = parameterMap;
        }
        // 单个参数
        else if (parameterObject instanceof String) {
            Parameter parameter = parameters[0];
            if (needEncrypt(parameter) && StrUtil.isNotBlank((String)parameterObject)) {
                invocation.getArgs()[1] = AesUtils.encrypt((String)parameterObject);
            }
        }
        // 自定义对象参数
        else {
            encryptObject(parameterObject);
        }

        //执行sql
        Object resultObject = invocation.proceed();

        if (ObjectUtil.isNotNull(resultObject)) {
            if (resultObject instanceof Collection) {
                Collection collection = (Collection) resultObject;
                if (CollectionUtil.isNotEmpty(collection)) {
                    for (Object o : collection) {
                        decryptByObject(o);
                    }
                }
            } else {
                decryptByObject(resultObject);
            }
        }
        return resultObject;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 对象加密，可递归，暂不实现
     * @param parameterObject
     * @return
     */
    @SneakyThrows
    private Object encryptObject(Object parameterObject) {
        //是否基本类型
        if (!ObjectUtil.isBasicType(parameterObject)) {
            Field[] fields = parameterObject.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (needEncryptStr(field) && StrUtil.isNotBlank((String)field.get(parameterObject))) {
                    field.set(parameterObject, AesUtils.encrypt((String)field.get(parameterObject)));
                }
            }
        }
        return parameterObject;
    }

    /**
     * 判断是否需要解密，且是字符串
     * @param field
     * @return
     */
    private boolean needEncryptStr(Field field) {
        return field.isAnnotationPresent(EncryptData.class) && field.getType().getSimpleName().equals("String");
    }

    /**
     * 判断是否需要解密
     * @param parameter
     * @return
     */
    private boolean needEncrypt(Parameter parameter) {
        return parameter.isAnnotationPresent(EncryptData.class);
    }

    /**
     * 解密对象
     * @param resultObject
     */
    private void decryptByObject(Object resultObject) {
        Field[] fields = resultObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            decryptByField(resultObject, field);
        }
    }

    /**
     * 解密属性值
     * @param resultObject
     * @param field
     */
    @SneakyThrows
    private void decryptByField(Object resultObject, Field field) {
        EncryptData encryptData = field.getAnnotation(EncryptData.class);
        if (ObjectUtil.isNotNull(encryptData)) {
            field.setAccessible(true);
            Object object = field.get(resultObject);
            if (object instanceof String && ObjectUtil.isNotNull(object)) {
                field.set(resultObject, AesUtils.decrypt((String)object));
            }
        }
    }
}
