package com.example.beans;

import com.example.constant.JsonSyntax;

import java.util.Map;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 20:04
 */
public class JsonItem implements Map.Entry<String, String>{
    private String key;
    private String value;

    public JsonItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public JsonItem(String[] keyAndValue){
        this(keyAndValue[0], keyAndValue[1]);
    }

    public JsonItem(Map.Entry<String, Object> entry) {
        this(entry.getKey(), (String) entry.getValue());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public String setValue(String value) {
        String oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    @Override
    public String toString() {
        return String.format("\"%s\"%s \"%s\"", key, JsonSyntax.COLON, value);
    }
}