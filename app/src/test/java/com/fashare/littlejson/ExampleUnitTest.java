package com.fashare.littlejson;

import com.fashare.littlejson.beans.JsonObject;
import com.fashare.littlejson.test.Person;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void main() throws Exception{

//        test();
//        oldTestObject(new Person(), Person.class);
        testObject(new Person(), Person.class);

//        testList(Arrays.asList(new Person(), new Person()), List.class);
    }

//    private void test() {
//        System.out.println(ClassUtil.wrapperIfPriive(JsonObject.class));
//    }

    private static <T> void oldTestObject(T t, Class<T> clazz) {
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

    private <T extends JavaBeanParsable> void testObject(T t, Class<T> clazz) {
        String jsonStr = t.toJson();
        System.out.println(jsonStr);

        jsonStr = "{\"name\" : \"\", \"age\": 20, \"score\": 4.0, \"is\": false, \"ch\": \'=\', \"stu\": {\"name\": \"stu\"}}";
//        System.out.println(t.fromJson(jsonStr).toJson() + "\n");

        Person p = new Person(), p1;
        p1 = p.fromJson(jsonStr);
        System.out.println(p1.toJson());

        jsonStr = "{\"name\" : \"aaa\", \"age\": 0, \"score\": 100, \"is\": true, \"ch\": \'.\', \"stu\": {\"name\": \"----\"}}";
        System.out.println(new JsonObject().parse(jsonStr).toJson() + "\n");
    }

//    private static <T> void testList(T t, Class<T> clazz) {
//        Object obj = t;
//        String jsonStr;
////        jsonStr = new Gson().toJson(obj);
////        System.out.println(jsonStr);
//        jsonStr = JsonParser.toJson(obj);
//        System.out.println(jsonStr);
//
//        jsonStr = "{\"name\" : \"\", \"age\": 20, \"score\": 4.0, \"is\": false, \"ch\": \'=\', \"stu\": {\"name\": \"stu\"}}";
////        Object obj2 = new Gson().fromJson(jsonStr, clazz);
//        Object newObj = JsonParser.fromJson(jsonStr, clazz);
//        System.out.println(JsonParser.toJson(newObj));
//        System.out.println("");
//    }

}