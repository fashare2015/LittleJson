package com.example.utils;

import com.example.beans.JsonObject;
import com.example.test.Person;

import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 18:03
 */
public class JsonParser {

    public static JsonObject parse(String jsonString){
        return Stream.of(jsonString)
                .filter(JsonSyntaxUtil:: checkSyntax)
                .map(JsonSyntaxUtil::getJsonItems)
                .map(JsonObject:: new)
                .findFirst().orElse(new JsonObject());
    }

    public static String toJson(Person person) {
        return Stream.of(person)
                .map(JsonClassLoader:: getJsonItems)
                .map(JsonObject:: new)
                .map(jsonObject -> jsonObject.toString())
                .findFirst().get();
    }
}