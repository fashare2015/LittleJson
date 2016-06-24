package com.example.utils;

import com.example.beans.JsonItem;
import com.example.beans.JsonObject;

import java.util.List;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 18:03
 */
public class JsonParser {

    public static JsonObject parse(String jsonString){
        JsonObject jsonObject = new JsonObject();
        if(JsonSyntaxUtil.checkSyntax(jsonString)) {
            List<JsonItem> items = JsonSyntaxUtil.getItems(jsonString);
            for (JsonItem item : items) {
                jsonObject.getJsonMap().put(item.getKey(), item.getValue());
            }
        }
        return jsonObject;
    }

}  