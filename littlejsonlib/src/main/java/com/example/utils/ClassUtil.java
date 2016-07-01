package com.example.utils;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 21:38
 */
public class ClassUtil {
//    private static final Pair<?, ?>[] primitivePairs = {
//            new Pair<>(int.class, Integer.class),
//            new Pair<>(byte.class, Byte.class),
//            new Pair<>(long.class, Long.class),
//            new Pair<>(double.class, Double.class),
//            new Pair<>(float.class, Float.class),
//            new Pair<>(char.class, Character.class),
//            new Pair<>(short.class, Short.class),
//            new Pair<>(boolean.class, Boolean.class)
//    };

    private static final HashMap<Class<?>, Class<?>> primitiveMap = new HashMap<Class<?>, Class<?>>(){{
            put(int.class, Integer.class);
            put(byte.class, Byte.class);
            put(long.class, Long.class);
            put(double.class, Double.class);
            put(float.class, Float.class);
            put(char.class, Character.class);
            put(short.class, Short.class);
            put(boolean.class, Boolean.class);
    }};

    /**
     * 判断一个类是否为基本数据类型。
     * @param clazz 要判断的类。
     * @return true 表示为基本数据类型。
     */
    public static boolean isBaseDataType(Class clazz){
        return primitiveMap.entrySet().stream()
                .flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
                .filter(aClass -> aClass.equals(clazz))
                .count() > 0;
    }

    /**
     * 如果是基本类型则转换为对应的包装类, 别的类型不变
     * @param rawClazz 原类型
     * @return 包装类型
     */
    public static Class wrapperIfPrimitive(Class rawClazz) {
        return Stream.of(rawClazz)
                .filter(Class:: isPrimitive)
                .map(aClass -> (Class)primitiveMap.get(aClass))
                .findFirst().orElse(rawClazz);
    }
}
