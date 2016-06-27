package com.example.constant;

import com.example.beans.JsonObject;
import com.example.JsonParser;

import java.util.function.Function;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 18:06
 * <br/><br/>
 * 在 {@link JsonObject.JsonItem} {key: value}中, <br/>
 * 根据 value 的不同类型, 用 Parser 作不同的解析 <br/>
 */
public class ValueLoader {

    /**
     * String: 输入的 value
     * @param <R> value 解析后返回的类型
     */
    public interface Parser<R> extends Function<String, R>{}

    public static final Parser<Integer> INT = str -> Integer.parseInt(str);
    public static final Parser<Double> DOUBLE = str -> Double.parseDouble(str);
    public static final Parser<Boolean> BOOLEAN = str -> Boolean.parseBoolean(str);
    public static final Parser<String> STRING = str -> str.substring(1, str.length()-1);
    public static final Parser<Character> CHAR = str -> str.charAt(1);
    public static final Parser<JsonObject> JSON_OBJECT = str -> JsonParser.parse(str);  // value 是嵌套的 jsonObject 的话, 递归下去!!!
    public static final Parser<String> JSON_ARRAY = str -> str; // TODO: value is JSON_ARRAY
}  