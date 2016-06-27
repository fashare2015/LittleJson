package com.example;

import com.example.beans.JsonObject;
import com.example.utils.JsonClassLoader;
import com.example.utils.JsonSyntaxUtil;

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
 * 3.parse(): JavaBean/JsonString -> JsonObject
 */
public class JsonParser {

    public static JsonObject parse(String jsonString){
        return Stream.of(jsonString)
                .map(JsonSyntaxUtil::getJsonItemList)   // 从 JsonString 按语法解析
                .map(JsonObject:: new)
                .findFirst().orElse(new JsonObject());
    }

    public static <T> JsonObject parse(T t){
        return Stream.of(t)
                .map(JsonClassLoader::getJsonItemList)  // 从 JavaBean 中通过反射解析
                .map(JsonObject:: new)
                .findFirst().orElse(new JsonObject());
    }

    public static <T> String toJson(T t) {
        return toJson(parse(t));
    }

    public static String toJson(JsonObject jsonObject){
        return jsonObject.toString();
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return fromJson(parse(jsonString), clazz);
    }

    public static <T> T fromJson(JsonObject jsonObject, Class<T> clazz) {
        return Stream.of(jsonObject)
                .map(mJsonObject -> JsonClassLoader.newInstance(mJsonObject, clazz))
                .findFirst().orElse(null);
    }
}