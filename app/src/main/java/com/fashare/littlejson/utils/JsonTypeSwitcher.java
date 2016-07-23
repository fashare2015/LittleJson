package com.fashare.littlejson.utils;


import com.fashare.littlejson.constant.JsonType;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by jinliangshan on 2016/6/28.
 * <br/><br/>
 * 根据 jsonString 的语法结构，找到对应的 JsonType, 并解析出 JavaBean.
 */
public class JsonTypeSwitcher {
    // ----------- JsonIO -----------
    public static <T> T read(String str){
        return Stream.of(JsonType.values())
                .map(type -> JsonTypeSwitcher.<T>read(str, type))
                .filter(Objects:: nonNull)
                .findFirst().orElse(null);
    }

    private static <T> T read(String str, JsonType jsonType){
        return (T)Stream.of(jsonType)
                .filter(type -> str != null && !str.equals(""))
//                .filter(type -> type != JsonType.ERROR)
                .filter(type -> RegexUtil.matchPattern(type.getTypePattern(), str))
                .flatMap(type -> Stream.of(str).map(type.getJsonIO().getReader()))
                .findFirst().orElse(null);
    }

    public static <T> String write(T t){
        return Stream.of(JsonType.values())
                .map(type -> JsonTypeSwitcher.write(t, type))
                .filter(Objects:: nonNull)
                .findFirst().orElse(null);
    }

    private static <T> String write(T t, JsonType jsonType){
        return (String) Stream.of(jsonType)
                .filter(type -> t != null)
//                .filter(type -> type != JsonType.ERROR)
                .filter(type -> type.getClazz().equals(t.getClass()))
                .flatMap(type -> Stream.of(t).map(type.getJsonIO().getWriter()))
                .findFirst().orElse(null);
    }

    // ----------- JavaBeanIO -----------
    public static <T> T read(Object bean) {
        return Stream.of(JsonType.values())
                .map(type -> JsonTypeSwitcher.<T>read(bean, type))
                .filter(Objects:: nonNull)
                .findFirst().orElse(null);
    }

    private static <T> T read(Object bean, JsonType jsonType){
        return (T)Stream.of(bean)
                .filter(Objects:: nonNull)
                .map(Object:: getClass)
                .map(ClassUtil:: wrapperIfPrimitive)    // 封装类
                .filter(jsonType:: matchClazz)
                .flatMap(aClass -> {
//                    System.out.println(jsonType.getClazz());
                    return Stream.of(bean).map(jsonType.getJavaBeanIO().getReader());
                })
                .map(o -> {
//                    System.out.println("read: " + o);
                    return o;
                })
                .findFirst().orElse(null);
    }

    public static <BEAN> BEAN write(Object t, Class<BEAN> beanClass){
        return Stream.of(JsonType.values())
                .map(type -> JsonTypeSwitcher.write(t, type, beanClass))
                .filter(Objects:: nonNull)
                .findFirst().orElse(null);
    }

    private static <BEAN> BEAN write(Object t, JsonType jsonType, Class<BEAN> beanClass){
        return (BEAN) Stream.of(jsonType)
                .filter(type -> t != null)
//                .filter(type -> type != JsonType.ERROR)
                .filter(type -> type.getClazz().equals(t.getClass()))
                .flatMap(type -> Stream.of(t).map(type.getJavaBeanIO(beanClass).getWriter()))
                .findFirst().orElse(null);
    }
}
