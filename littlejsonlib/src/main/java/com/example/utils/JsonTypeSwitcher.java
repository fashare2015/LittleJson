package com.example.utils;

import com.example.constant.JsonType;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by jinliangshan on 2016/6/28.
 * <br/><br/>
 * 根据 jsonString 的语法结构，找到对应的 JsonType, 并解析出 JavaBean.
 */
public class JsonTypeSwitcher {
    public static <T> T read(String str){
        return Stream.of(JsonType.values())
                .map(type -> JsonTypeSwitcher.<T>read(str, type))
                .filter(Objects:: nonNull)
                .findFirst().orElse(null);
    }

    private static <T> T read(String str, JsonType jsonType){
        return (T)Stream.of(jsonType)
                .filter(type -> str != null && !str.equals(""))
                .filter(type -> type != JsonType.ERROR)
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
                .filter(type -> type != JsonType.ERROR)
                .filter(type -> type.getClazz().equals(t.getClass()))
                .flatMap(type -> Stream.of(t).map(type.getJsonIO().getWriter()))
                .findFirst().orElse(null);
    }
}
