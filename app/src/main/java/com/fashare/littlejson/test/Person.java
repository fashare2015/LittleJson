package com.fashare.littlejson.test;


import com.fashare.littlejson.JavaBeanParsable;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 19:23
 */
public class Person implements JavaBeanParsable {
    private String name = "person";
    private int age = 22;
    private double score = 3.7;
    private boolean is = true;
    private char ch = 'c';
    private Student stu = new Student();

//    @Override
//    public String toString() {
//        return JsonParser.toStr(this);
//    }
}
