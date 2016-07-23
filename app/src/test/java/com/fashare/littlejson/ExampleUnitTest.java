package com.fashare.littlejson;

import com.fashare.littlejson.beans.JsonObject;
import com.fashare.littlejson.test.Person;
import com.fashare.littlejson.test.Student;
import com.fashare.littlejson.utils.JsonTypeSwitcher;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
//        System.out.println(JsonType.showClass(ClassUtil.wrapperIfPrimitive(Person.class)));

//        System.out.println(JsonTypeSwitcher.read(new Person()).getClass());
        List<List<Person>> personList = new ArrayList<List<Person>>();
//        System.out.println(ReflectUtils.getClassGenericType(personList.getClass()));
//        System.out.println(ReflectUtils.getSuperclassTypeParameter(personList.getClass()));
//        System.out.println(personList.getClass());
//        System.out.println(new TypeToken<List<Person>>(){}.getType());
//        System.out.println(TypeToken.<Object>get(new TypeToken<List<Person>>(){}.getType()).getType());

//        test();
//        oldTestObject(new Person(), Person.class);
//        testObject(new Person(), Person.class);
//        oldTestObject(new Student(), Student.class);

        List<Person> persons = Arrays.asList(new Person(), new Person());
        System.out.println(persons.getClass().getTypeParameters()[0]);
//        testList(persons, persons.getClass());
        ParameterizedType type = (ParameterizedType)new TypeToken<ArrayList<Person>>(){}.getType();
        System.out.println(type);
        System.out.println(type instanceof ParameterizedType);
        System.out.println(type.getRawType());
        System.out.println(type.getOwnerType());
//        Type pt = ((ParameterizedType)type).getActualTypeArguments()[0];
//        System.out.println(pt);
//        System.out.println(pt instanceof Class);
        testList(persons, type);
        testList(new LinkedList<Student>(){{add(new Student()); add(new Student());}},
                (ParameterizedType)new TypeToken<LinkedList<Student>>(){}.getType());

//        String jsonStr = "[{\"name\" : \"1\", \"age\": 20, \"score\": 4.0}, {\"name\" : \"2\", \"age\": 10, \"score\": 3.7}]";
//        List list = JsonParser.fromJson(jsonStr, new ArrayList<Person>().getClass());
//        System.out.println(JsonParser.toJson(list));

        double one = JsonTypeSwitcher.read("2.1");
        System.out.printf(one + "");

        System.out.println(new LinkedList<>() instanceof List);

    }

//    private void test() {
//        System.out.println(ClassUtil.wrapperIfPriive(JsonElement.class));
//    }

    private static <T> void oldTestObject(T t, Class<T> clazz) {
        Object obj = t;
        String jsonStr;
//        jsonStr = new Gson().toStr(obj);
//        System.out.println(jsonStr);
//        System.out.println("jsonElement:" + JsonParser.parse(t).getJsonItemList());

        jsonStr = JsonParser.toJson(obj);
        System.out.println(jsonStr);

        jsonStr = "{\"name\" : \"\", \"age\": 20, \"score\": 4.0, \"is\": false, \"ch\": \'=\', \"stu\": {\"name\": \"stu\"}}";
        Object newObj = JsonParser.fromJson(jsonStr, clazz);
        System.out.println(newObj.getClass());
        System.out.println(JsonParser.toJson(newObj));
        System.out.println("--------------\n");
    }

    private <T extends JavaBeanParsable> void testObject(T t, Class<T> clazz) {
        String jsonStr = t.toJson();
        System.out.println(jsonStr);

        jsonStr = "{\"name\" : \"aaa\", \"age\": 0, \"score\": 100, \"is\": true, \"ch\": \'.\', \"stu\": {\"name\": \"----\"}}";
        T newObj = t.fromJson(jsonStr);
        System.out.println(newObj.getClass());
        System.out.println(new JsonObject().fromStr(jsonStr).toStr() + "\n");
    }

    private static <T extends List> void testList(T t, ParameterizedType type) {
        Object obj = t;
        String jsonStr;

//        System.out.println(new Gson().toStr(JavaBeanIOUtil.parseJsonElement(new Person())));
//        System.out.println(new Gson().toStr(JsonParser.fromBean(obj)));

//        JsonElement jsonElement = JsonParser.parse(t);
//        System.out.println("");
//        System.out.println("jsonList" + jsonElement.getJsonItemList());
//        System.out.println(new Gson().toJson(jsonElement));

        jsonStr = JsonParser.toJson(obj);
        System.out.println("--- testList --- :" + jsonStr);

        jsonStr = "[{\"name\" : \"1\", \"age\": 20, \"score\": 4.0}, {\"name\" : \"2\", \"age\": 10, \"score\": 3.7}]";
        List<Person> newObj = JsonParser.fromJson(jsonStr, type);
        System.out.println(newObj.getClass());
        System.out.println(JsonParser.toJson(newObj));


//        List<T> newObj = new Gson().toBean(jsonStr, new LinkedList<T>().getClass());
//        System.out.println(newObj.getClass());
//        System.out.println(new Gson().toStr(newObj));

        System.out.println("-----------\n");
    }

}