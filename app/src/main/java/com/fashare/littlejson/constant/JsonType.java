package com.fashare.littlejson.constant;

import com.fashare.littlejson.beans.JsonArray;
import com.fashare.littlejson.beans.JsonObject;
import com.fashare.littlejson.interfaces.JavaBeanIO;
import com.fashare.littlejson.interfaces.JsonIO;

import java.util.List;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 18:06
 * <br/><br/>
 * 在 {@link JsonObject.JsonObjectItem} {key: value}中,
 * 指定 key 或 value 的可取的类型
 */
public enum JsonType {
    STRING(RegexPattern.STRING, JsonIOs.STRING, JavaBeanIOs.STRING, String.class),
    INT(RegexPattern.INT, JsonIOs.INT, JavaBeanIOs.INT, Integer.class),
    DOUBLE(RegexPattern.DOUBLE, JsonIOs.DOUBLE, JavaBeanIOs.DOUBLE, Double.class),
    CHAR(RegexPattern.CHAR, JsonIOs.CHAR, JavaBeanIOs.CHAR, Character.class),
    BOOLEAN(RegexPattern.BOOLEAN, JsonIOs.BOOLEAN, JavaBeanIOs.BOOLEAN, Boolean.class),
    JSON_ARRAY(RegexPattern.JSON_ARRAY, JsonIOs.JSON_ARRAY, JavaBeanIOs.JSON_ARRAY, JsonArray.class),
    JSON_OBJECT(RegexPattern.JSON_OBJECT, JsonIOs.JSON_OBJECT, JavaBeanIOs.JSON_OBJECT, JsonObject.class);
//    ERROR("ERROR", null, null, null);

    private String typePattern;
    private JsonIO mJsonIO;
    private JavaBeanIO mJavaBeanIO;
    private Class<? extends Object> clazz;

    JsonType(String typePattern, JsonIO jsonIO, JavaBeanIO javaBeanIO, Class<? extends Object> clazz) {
        this.typePattern = typePattern;
        mJsonIO = jsonIO;
        mJavaBeanIO = javaBeanIO;
        this.clazz = clazz;
    }

    public String getTypePattern() {
        return typePattern;
    }

    public JsonIO getJsonIO() {
        return mJsonIO;
    }

    public JavaBeanIO getJavaBeanIO() {
        return mJavaBeanIO;
    }

    public JavaBeanIO getJavaBeanIO(Class<?> beanClazz) {
        mJavaBeanIO.setBeanClass(beanClazz);
        return mJavaBeanIO;
    }

    public Class<? extends Object> getClazz() {
        return clazz;
    }

    public Class<? extends Object> getJavaBeanClazz() {
        return clazz.equals(JsonArray.class)? List.class:
                clazz.equals(JsonObject.class)? Object.class:
                clazz;
    }

    /**
     * @param aClass
     * @return type.getJavaBeanClazz() 是 aClass 的父类
     */
    public boolean matchClazz(Class aClass) {
        return this.getJavaBeanClazz().isAssignableFrom(aClass);
    }

    public static Class<? extends Object> showClass(Class aClass) {
        return Stream.of(JsonType.values())
                .filter(type -> type.getJavaBeanClazz().isAssignableFrom(aClass))
                .map(type -> type.getJavaBeanClazz())
                .findFirst().orElse(null);
    }
}
