package com.example.constant;

import com.example.utils.RegexUtil;
import com.example.beans.*;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 18:06
 * <br/><br/>
 * 在 {@link JsonObject.JsonItem} {key: value}中,
 * 指定 value 的可取的类型
 */
public enum ValueType {
    STRING(RegexPattern.STRING, ValueLoader.STRING),
    INT(RegexPattern.INT, ValueLoader.INT),
    DOUBLE(RegexPattern.DOUBLE, ValueLoader.DOUBLE),
    CHAR(RegexPattern.CHAR, ValueLoader.CHAR),
    BOOLEAN(RegexPattern.BOOLEAN, ValueLoader.BOOLEAN),
    JSON_OBJECT(RegexPattern.JSON_OBJECT, ValueLoader.JSON_OBJECT),
    JSON_ARRAY(RegexPattern.JSON_ARRAY, ValueLoader.JSON_ARRAY),
    ERROR("ERROR", null);

    private String typePattern;
    private ValueLoader.Parser parser;

    ValueType(String typePattern, ValueLoader.Parser parser) {
        this.typePattern = typePattern;
        this.parser = parser;
    }

    public static Object getValue(String str){
        return Stream.of(ValueType.values())
                .map(type -> getValue(str, type))
                .filter(Objects:: nonNull)
                .findFirst().orElse(null);
    }

    public static Object getValue(String str, ValueType valueType){
        return Stream.of(valueType)
                .filter(type -> str != null && !str.equals(""))
                .filter(type -> type != ERROR)
                .filter(type -> RegexUtil.matchPattern(type.typePattern, str))
                .flatMap(type -> Stream.of(str).map(type.parser))
                .findFirst().orElse(null);
    }

}
