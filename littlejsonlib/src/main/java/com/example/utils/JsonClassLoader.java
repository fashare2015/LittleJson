package com.example.utils;

import com.example.beans.JsonObject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-25
 * Time: 23:57
 * <br/><br/>
 * 类的解析工具(通过反射)
 */
public class JsonClassLoader {

    public static List<JsonObject.JsonItem> getJsonItemList(Object obj) {
        return Stream.of(obj.getClass().getDeclaredFields())    // getFields
                .map(field -> getJsonItem(field, obj))
                .filter(jsonItem -> jsonItem != null)
                .collect(Collectors.toList());
    }

    private static JsonObject.JsonItem getJsonItem(Field field, Object fatherObj){
        JsonObject.JsonItem jsonItem = null;
        try {
            String key = field.getName();
            Object value = null;

            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            {
                value = field.get(fatherObj);
                if(!ClassUtil.isBaseDataType(value.getClass()))   // value不是基本类型,递归下去,得到jsonObject
//                    value = new JsonObject(getJsonItemList(value));
                    value = com.example.JsonParser.parse(value);
                jsonItem = new JsonObject.JsonItem(key, value);
            }
            field.setAccessible(accessible);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return jsonItem;
    }

    public static <T> T newInstance(JsonObject jsonObject, Class<T> clazz) {
        return newInstance(jsonObject.getJsonItemList(), clazz);
    }

    private static <T> T newInstance(List<JsonObject.JsonItem> jsonItems, Class<T> clazz) {
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
        return (T)object;
    }

    private static void setField(Field[] fields, Object fatherObj, final JsonObject.JsonItem jsonItem) {
        Stream.of(fields).filter(field -> field.getName().equals(jsonItem.getKey()))
                .forEach(field -> {
                    try {
                        Object value = jsonItem.getValue();
                        boolean accessible = field.isAccessible();

                        field.setAccessible(true);
                        {
                            if(value instanceof JsonObject) // value不是基本类型, 递归下去
                                value = com.example.JsonParser.fromJson((JsonObject) value, field.getType());
                            field.set(fatherObj, value);
                        }
                        field.setAccessible(accessible);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }
}