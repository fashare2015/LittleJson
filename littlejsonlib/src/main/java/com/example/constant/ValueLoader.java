package com.example.constant;

import java.util.function.Function;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 18:06
 */
public class ValueLoader {
    public interface Parser<R> extends Function<String, R>{}

    public static final Parser<Integer> INT = str -> Integer.parseInt(str);
    public static final Parser<Double> DOUBLE = str -> Double.parseDouble(str);
    public static final Parser<Boolean> BOOLEAN = str -> Boolean.parseBoolean(str);
    public static final Parser<String> STRING = str -> str.substring(1, str.length()-1);
    public static final Parser<Character> CHAR = str -> str.charAt(1);
    public static final Parser<String> JSON_OBJECT = STRING;
    public static final Parser<String> JSON_ARRAY = STRING;
}  