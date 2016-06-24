package com.example.beans;

import java.util.HashMap;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 17:23
 */
public class JsonObject {
    protected HashMap<String, Object> jsonMap = new HashMap<>();

    public HashMap<String, Object> getJsonMap() {
        return jsonMap;
    }

    public String getString(String key){
        if(key != null)
            return (String)jsonMap.get(key);
        return "null";
    }

    @Override
    public String toString() {
        return jsonMap.toString();
    }
}