package com.example.test;

import com.example.JsonParser;
import com.example.beans.JsonObject;
import com.example.utils.ClassUtil;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 19:22
 * <br/><br/>
 * 用于测试 Json 库
 */
public class Main {
    public static void main(String[] args) {

//        test();
        testJson(new Person(), Person.class);
        testJson(new Student(), Student.class);
    }

    private static void test() {
        System.out.println(ClassUtil.wrapperIfPrimitive(JsonObject.class));
    }

    private static <T> void testJson(T t, Class<T> clazz) {
        Object obj = t;
        String jsonStr;
//        jsonStr = new Gson().toJson(obj);
//        System.out.println(jsonStr);
        jsonStr = JsonParser.toJson(obj);
        System.out.println(jsonStr);

        jsonStr = "{\"name\" : \"\", \"age\": 20, \"score\": 4.0, \"is\": false, \"ch\": \'=\', \"stu\": {\"name\": \"stu\"}}";
//        Object obj2 = new Gson().fromJson(jsonStr, clazz);
        Object newObj = JsonParser.fromJson(jsonStr, clazz);
        System.out.println(JsonParser.toJson(newObj));
        System.out.println("");
    }


}
