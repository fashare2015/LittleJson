package com.example.test;

import com.google.gson.Gson;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 19:23
 */
public class Student {
    private String name = "student";
    private int age = 22;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}