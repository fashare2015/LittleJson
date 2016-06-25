package com.example.utils;

import com.example.beans.JsonItem;

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

    public static List<JsonItem> getJsonItems(Object obj) {
        return Stream.of(obj)
                .flatMap(o -> Stream.of(o.getClass().getDeclaredFields()))  // getFields
                .map(field -> convertToKeyAndValue(field, obj))
                .filter(JsonSyntaxUtil:: isKeyAndValueNotNull)
                .map(JsonItem:: new)    // 转换为 JsonItem
                .collect(Collectors.toList());
    }

    public static String[] convertToKeyAndValue(Field field, Object fatherObj){
        String[] keyAndValue = new String[2];
        try {
            keyAndValue[0] = field.getName();
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            keyAndValue[1] = (String) field.get(fatherObj);
            field.setAccessible(accessible);
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return keyAndValue;
    }
}