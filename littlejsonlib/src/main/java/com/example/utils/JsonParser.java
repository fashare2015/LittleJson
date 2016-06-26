package com.example.utils;

import com.example.beans.JsonItem;
import com.example.beans.JsonObject;
import com.example.test.Person;

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

    private static Stream<List<JsonItem>> getJsonItemList(String jsonString) {
        return Stream.of(jsonString)
                .filter(JsonSyntaxUtil:: checkSyntax)
                .map(JsonSyntaxUtil::getJsonItemList);
    }

    public static String toJson(Person person) {
        return Stream.of(person)
                .map(JsonClassLoader::getJsonItemList)
                .map(JsonObject:: new)
                .map(jsonObject -> jsonObject.toString())
                .findFirst().orElse("");
    }


    public static Person fromJson(String jsonString, Class<Person> clazz) {
        return getJsonItemList(jsonString)
                .map(jsonItems -> (Person)JsonClassLoader.newInstance(jsonItems, clazz))
                .findFirst().orElse(null);
    }
}