package com.example;

import com.example.beans.JsonObject;
import com.example.utils.JsonTypeSwitcher;

import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 18:03
 * <br/><br/>
 * Json 解析类, 以 JsonObject 为桥梁, 实现 JavaBean 和 JsonString 之间的相互映射.
 * 主要有三个函数:
 * 1.toJson(): JavaBean/JsonObject -> JsonString
 * 2.fromJson(): JsonString/JsonObject -> JavaBean
 * 3.parseJsonObject(): JavaBean/JsonString -> JsonObject
 */
public class JsonParser {

    public static JsonObject parse(String jsonString){
        return Stream.of(jsonString)
                .map(JsonTypeSwitcher:: <JsonObject>read)
                .findFirst().orElse(new JsonObject());
    }

    public static <BEAN> JsonObject parse(BEAN bean){
        return Stream.of(bean)
                .map(JsonTypeSwitcher:: <JsonObject>read)   // 从 JavaBean 中通过反射解析
                .findFirst().orElse(new JsonObject());
    }

    public static <BEAN> String toJson(BEAN bean) {
        return toJson(parse(bean));
    }

    public static String toJson(JsonObject jsonObject){
        return Stream.of(jsonObject)
                .map(JsonTypeSwitcher:: write)
                .findFirst().orElse(null);
    }

    public static <BEAN> BEAN fromJson(String jsonString, Class<BEAN> clazz) {
        return fromJson(parse(jsonString), clazz);
    }

    public static <BEAN> BEAN fromJson(JsonObject jsonObject, Class<BEAN> clazz) {
        return Stream.of(jsonObject)
                .map(mJsonObject -> JsonTypeSwitcher.write(mJsonObject, clazz))
                .findFirst().orElse(null);
    }
}
