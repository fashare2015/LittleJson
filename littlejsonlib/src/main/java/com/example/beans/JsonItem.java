package com.example.beans;

import com.google.gson.Gson;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 20:04
 */
public class JsonItem {
    private String key;
    private String value;

    public JsonItem(String key, String value) {
        this.key = key;
        this.value = value;
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

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}