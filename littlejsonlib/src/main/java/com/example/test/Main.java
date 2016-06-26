package com.example.test;

import com.example.beans.JsonObject;
import com.example.utils.JsonParser;
import com.google.gson.Gson;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 19:22
 */
public class Main {
    public static void main(String[] args) {

        testJson(new Person(), Person.class);

        testJson(new Student(), Student.class);
    }

    private static <T> void testJson(T t, Class<T> clazz) {
        Object obj = t;
        String jsonStr;
        jsonStr = new Gson().toJson(obj);
        System.out.println(jsonStr);

        jsonStr = JsonParser.toJson(obj);
        System.out.println(jsonStr);
//        System.out.println(JsonSyntaxUtil.checkSyntax(jsonStr));

        JsonObject jsonObject = JsonParser.parse(jsonStr);
        System.out.println("" + jsonObject.getObject("name") + jsonObject.getObject("age")
                + jsonObject.getObject("score") + jsonObject.getObject("is"));

        jsonStr = "{\"name\" : \"\", \"age\": 20, \"score\": 4.0, \"is\": false}";

        Object newObj = JsonParser.fromJson(jsonStr, clazz);
        System.out.println(newObj.toString());

        System.out.println("");
    }


}