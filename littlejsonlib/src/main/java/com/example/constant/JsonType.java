package com.example.constant;

import com.example.beans.JsonArray;
import com.example.beans.JsonObject;
import com.example.interfaces.JsonIO;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 18:06
 * <br/><br/>
 * 在 {@link JsonObject.JsonItem} {key: value}中,
 * 指定 key 或 value 的可取的类型
 */
public enum JsonType {
    STRING(RegexPattern.STRING, JsonIOs.STRING, String.class),
    INT(RegexPattern.INT, JsonIOs.INT, Integer.class),
    DOUBLE(RegexPattern.DOUBLE, JsonIOs.DOUBLE, Double.class),
    CHAR(RegexPattern.CHAR, JsonIOs.CHAR, Character.class),
    BOOLEAN(RegexPattern.BOOLEAN, JsonIOs.BOOLEAN, Boolean.class),
    JSON_OBJECT(RegexPattern.JSON_OBJECT, JsonIOs.JSON_OBJECT, JsonObject.class),
    JSON_ARRAY(RegexPattern.JSON_ARRAY, JsonIOs.JSON_ARRAY, JsonArray.class),
    ERROR("ERROR", null, null);

    private String typePattern;
    private JsonIO mJsonIO;
    private Class<? extends Object> clazz;

    JsonType(String typePattern, JsonIO jsonIO, Class clazz) {
        this.typePattern = typePattern;
        this.mJsonIO = jsonIO;
        this.clazz = clazz;
    }

    public String getTypePattern() {
        return typePattern;
    }

    public JsonIO getJsonIO() {
        return mJsonIO;
    }

    public Class<? extends Object> getClazz() {
        return clazz;
    }
}
