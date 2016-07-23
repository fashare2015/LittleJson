package com.fashare.littlejson;

import com.fashare.littlejson.beans.JsonElement;
import com.fashare.littlejson.utils.JsonTypeSwitcher;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 18:03
 * <br/><br/>
 * Json 解析类, 以 JsonElement 为桥梁, 实现 JavaBean 和 JsonString 之间的相互映射.
 * 主要有三个函数:
 * 1.toStr(): JavaBean/JsonElement -> JsonString
 * 2.toBean(): JsonString/JsonElement -> JavaBean
 * 3.parseJsonElement(): JavaBean/JsonString -> JsonElement
 */
public class JsonParser {
    // TODO 状态不可是 static
    private static ParameterizedType parameterizedType;  // 带参数的类型(范型)
    private static Class<?> javaBeanClazz;

    public static ParameterizedType getParameterizedType() {
        return parameterizedType;
    }

    public static Class<?> getJavaBeanClazz() {
        return javaBeanClazz;
    }

    public static JsonElement parse(String jsonString){
        return Stream.of(jsonString)
                .map(JsonTypeSwitcher:: <JsonElement>read)
                .filter(Objects:: nonNull)
                .findFirst().orElse(null);
    }

    public static <BEAN> JsonElement parse(BEAN bean){
        return Stream.of(bean)
                .map(JsonTypeSwitcher:: <JsonElement>read)   // 从 JavaBean 中通过反射解析
                .filter(Objects:: nonNull)
                .findFirst().orElse(null);
    }

    public static <BEAN> String toJson(BEAN bean) {
        return toJson(parse(bean));
    }

    public static String toJson(JsonElement jsonElement){
        return Stream.of(jsonElement)
                .map(JsonTypeSwitcher:: write)
                .filter(Objects:: nonNull)
                .findFirst().orElse(null);
    }

    public static <BEAN> BEAN fromJson(String jsonString, Class<BEAN> clazz) {
        return fromJson(parse(jsonString), clazz);
    }

    public static <BEAN> BEAN fromJson(JsonElement jsonElement, Class<BEAN> clazz) {
        return Stream.of(jsonElement)
                .map(mJsonObject -> JsonTypeSwitcher.write(mJsonObject, clazz))
                .filter(Objects:: nonNull)
                .findFirst().orElse(null);
    }

    public static <BEAN> BEAN fromJson(String jsonString, ParameterizedType type) {
        JsonParser.parameterizedType = type;
        JsonParser.javaBeanClazz = (Class<BEAN>) type.getRawType();
        return fromJson(jsonString, (Class<BEAN>) javaBeanClazz);
    }
}
