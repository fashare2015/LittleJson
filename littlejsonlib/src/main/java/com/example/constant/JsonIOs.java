package com.example.constant;

import com.example.beans.JsonArray;
import com.example.beans.JsonObject;
import com.example.interfaces.JsonIO;
import com.example.utils.JsonIOUtil;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 18:06
 * <br/><br/>
 * 定义了一些常用的 JsonIO 类型 <br/>
 */
public class JsonIOs {

    public static final JsonIO<Integer> INT = new JsonIO<>(Integer:: parseInt);

    public static final JsonIO<Double> DOUBLE = new JsonIO<>(Double:: parseDouble);

    public static final JsonIO<Boolean> BOOLEAN = new JsonIO<>(Boolean:: parseBoolean);

    public static final JsonIO<String> STRING = new JsonIO<>(
            str -> str.substring(1, str.length()-1),
            str -> JsonIOUtil.surroundBy(str, JsonSyntax.DOUBLE_QUOTE)
    );

    public static final JsonIO<Character> CHAR = new JsonIO<>(
            str -> str.charAt(1),
            character -> JsonIOUtil.surroundBy(character, JsonSyntax.SINGLE_QUOTE)
    );

    public static final JsonIO<JsonObject> JSON_OBJECT = new JsonIO<>(JsonIOUtil:: parseJsonObject);

    public static final JsonIO<JsonArray> JSON_ARRAY = new JsonIO<>(
            null,
            null
    );
    // TODO: value is JSON_ARRAY
}  
