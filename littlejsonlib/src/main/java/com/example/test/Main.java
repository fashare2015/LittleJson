package com.example.test;

import com.example.beans.JsonObject;
import com.example.utils.JsonParser;
import com.example.utils.JsonSyntaxUtil;
import com.google.gson.Gson;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 19:22
 */
public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        String jsonStr;
        jsonStr = new Gson().toJson(person);
        System.out.println(jsonStr);

        jsonStr = JsonParser.toJson(person);
//        jsonStr = "{\"name\":\"sdsd\"}";
        System.out.println(jsonStr);

        System.out.println(JsonSyntaxUtil.checkSyntax(jsonStr));

        JsonObject jsonObject = JsonParser.parse(jsonStr);
        System.out.println(jsonObject.getString("name") + jsonObject.getString("age") + jsonObject.getString("ssss"));
    }
}  