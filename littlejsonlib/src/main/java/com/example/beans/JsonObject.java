package com.example.beans;

import com.example.constant.JsonSyntax;

import java.util.HashMap;
import java.util.List;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 17:23
 */
public class JsonObject {
    protected HashMap<String, Object> jsonMap = new HashMap<>();

    public JsonObject(){}

    public JsonObject(List<JsonItem> jsonItems) {
        jsonItems.stream().forEach(this:: putItem);
    }

    public void putItem(JsonItem jsonItem) {
        jsonMap.put(jsonItem.getKey(), jsonItem.getValue());
    }

    public String getString(String key){
        if(key != null)
            return (String)jsonMap.get(key);
        return "null";
    }

    @Override
    public String toString() {
        return "" + JsonSyntax.BRACE_BEGIN
                + jsonMap.entrySet().stream()
                        .map(JsonItem:: new)
                        .map(JsonItem:: toString)
                        .reduce(this:: mergeString)
                        .orElse("")
                + JsonSyntax.BRACE_END;
    }

    private String mergeString(String a, String b) {
        return a + ("" + JsonSyntax.SEPARATOR+" ") + b;
    }

}