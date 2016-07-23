package com.fashare.littlejson.constant;

import com.fashare.littlejson.beans.JsonArray;
import com.fashare.littlejson.beans.JsonElement;
import com.fashare.littlejson.beans.JsonObject;
import com.fashare.littlejson.interfaces.JsonIO;

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
            str -> JsonElement.StrIOUtil.surroundBy(str, JsonSyntax.DOUBLE_QUOTE)
    );

    public static final JsonIO<Character> CHAR = new JsonIO<>(
            str -> str.charAt(1),
            character -> JsonElement.StrIOUtil.surroundBy(character, JsonSyntax.SINGLE_QUOTE)
    );

    public static final JsonIO<JsonElement> JSON_OBJECT = new JsonIO<>(
            JsonObject:: create,
            JsonElement:: toStr
    );

    public static final JsonIO<JsonElement> JSON_ARRAY = new JsonIO<>(
            JsonArray:: create,
            JsonElement:: toStr
    );
    // TODO: value is JSON_ARRAY
}  
