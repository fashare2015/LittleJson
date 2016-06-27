package com.example.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 21:38
 */
public class ClassUtil {
    private static final Class[] baseClasses = {
            String.class, Integer.class, Byte.class, Long.class,
            Double.class, Float.class, Character.class, Short.class,
            BigDecimal.class, BigInteger.class, Boolean.class
    };

    /**
     * 判断一个类是否为基本数据类型。
     * @param clazz 要判断的类。
     * @return true 表示为基本数据类型。
     */
    public static boolean isBaseDataType(Class clazz){
        return Stream.of(baseClasses)
                .filter(aClass -> aClass.equals(clazz) || clazz.isPrimitive())
                .count() > 0;
    }
}  