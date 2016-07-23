package com.fashare.littlejson.test;

import com.fashare.littlejson.JavaBeanParsable;
import com.fashare.littlejson.beans.JsonElement;
import com.fashare.littlejson.beans.JsonObject;
import com.fashare.littlejson.utils.ClassUtil;

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
//        testObject(new Person(), Person.class);
//        testObject(new Student(), Student.class);

//        testList(Arrays.asList(new Person(), new Person()), List.class);
    }

    private static void test() {
        System.out.println(ClassUtil.wrapperIfPrimitive(JsonElement.class));
    }

    //    private static <T> void testObject(T t, Class<T> clazz) {
//        Object obj = t;
//        String jsonStr;
////        jsonStr = new Gson().toStr(obj);
////        System.out.println(jsonStr);
//        jsonStr = JsonParser.toStr(obj);
//        System.out.println(jsonStr);
//
//        jsonStr = "{\"name\" : \"\", \"age\": 20, \"score\": 4.0, \"is\": false, \"ch\": \'=\', \"stu\": {\"name\": \"stu\"}}";
////        Object obj2 = new Gson().toBean(jsonStr, clazz);
//        Object newObj = JsonParser.toBean(jsonStr, clazz);
//        System.out.println(JsonParser.toStr(newObj));
//        System.out.println("");
//    }
    private static <T extends JavaBeanParsable> void testObject(T t, Class<T> clazz) {
        String jsonStr = t.toJson();
        System.out.println(jsonStr);

        jsonStr = "{\"name\" : \"\", \"age\": 20, \"score\": 4.0, \"is\": false, \"ch\": \'=\', \"stu\": {\"name\": \"stu\"}}";
        System.out.println(t.fromJson(jsonStr).toJson() + "\n");

//        Person p = new Person(), p1;
//        p1 = p.toBean(jsonStr);
//        System.out.println(p1);

        jsonStr = "{\"name\" : \"aaa\", \"age\": 0, \"score\": 100, \"is\": true, \"ch\": \'.\', \"stu\": {\"name\": \"----\"}}";
        System.out.println(new JsonObject().fromStr(jsonStr).toStr() + "\n");
    }

//    private static <T> void testList(T t, Class<T> clazz) {
//        Object obj = t;
//        String jsonStr;
////        jsonStr = new Gson().toStr(obj);
////        System.out.println(jsonStr);
//        jsonStr = JsonParser.toStr(obj);
//        System.out.println(jsonStr);
//
//        jsonStr = "{\"name\" : \"\", \"age\": 20, \"score\": 4.0, \"is\": false, \"ch\": \'=\', \"stu\": {\"name\": \"stu\"}}";
////        Object obj2 = new Gson().toBean(jsonStr, clazz);
//        Object newObj = JsonParser.toBean(jsonStr, clazz);
//        System.out.println(JsonParser.toStr(newObj));
//        System.out.println("");
//    }

}
