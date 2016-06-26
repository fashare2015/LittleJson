package com.example.utils;

import com.example.beans.JsonObject;

import java.util.List;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 18:03
 */
public class JsonParser {

    public static JsonObject parse(String jsonString){
        return getJsonItemList(jsonString)
                .map(JsonObject:: new)
                .findFirst().orElse(new JsonObject());
    }

    private static Stream<List<JsonObject.JsonItem>> getJsonItemList(String jsonString) {
        return Stream.of(jsonString)
//                .filter(JsonSyntaxUtil:: checkSyntax)
                .map(JsonSyntaxUtil::getJsonItemList);
    }

    public static <T> String toJson(T t) {
        return Stream.of(t)
                .map(JsonClassLoader::getJsonItemList)
                .map(JsonObject:: new)
                .map(jsonObject -> jsonObject.toString())
                .findFirst().orElse("");
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return getJsonItemList(jsonString)
                .map(jsonItems -> JsonClassLoader.newInstance(jsonItems, clazz))
                .findFirst().orElse(null);
    }
}