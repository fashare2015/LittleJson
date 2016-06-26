package com.example.utils;

import com.example.beans.JsonItem;
import com.example.test.Person;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-25
 * Time: 23:57
 */
public class JsonClassLoader {

    public static List<JsonItem> getJsonItemList(Object obj) {
        return Stream.of(obj)
                .flatMap(o -> Stream.of(o.getClass().getDeclaredFields()))  // getFields
                .map(field -> getJsonItem(field, obj))
                .filter(jsonItem -> jsonItem != null)
                .collect(Collectors.toList());
    }

    private static JsonItem getJsonItem(Field field, Object fatherObj){
        JsonItem jsonItem = null;
        try {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            jsonItem = new JsonItem(field.getName(), (String) field.get(fatherObj));
            field.setAccessible(accessible);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return jsonItem;
    }

    public static Object newInstance(List<JsonItem> jsonItems, Class<Person> clazz) {
        Object object = null;
        try {
            final Object fatherObj = object = clazz.newInstance();
            jsonItems.stream().forEach(jsonItem ->
                    setField(clazz.getDeclaredFields(), fatherObj, jsonItem));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static void setField(Field[] fields, Object fatherObj, final JsonItem jsonItem) {
        Stream.of(fields).filter(field -> field.getName().equals(jsonItem.getKey()))
                .forEach(field -> {
                    try {
                        boolean accessible = field.isAccessible();
                        field.setAccessible(true);
                        field.set(fatherObj, jsonItem.getValue());
                        field.setAccessible(accessible);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }
}