package com.fashare.littlejson.constant;


import com.fashare.littlejson.beans.JsonArray;
import com.fashare.littlejson.beans.JsonElement;
import com.fashare.littlejson.beans.JsonObject;
import com.fashare.littlejson.interfaces.JavaBeanIO;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 18:06
 * <br/><br/>
 * 定义了一些常用的 JavaBeanIO 类型 <br/>
 */
public class JavaBeanIOs {

    public static final JavaBeanIO<Integer> INT = new JavaBeanIO<>();

    public static final JavaBeanIO<Double> DOUBLE = new JavaBeanIO<>();

    public static final JavaBeanIO<Boolean> BOOLEAN = new JavaBeanIO<>();

    public static final JavaBeanIO<String> STRING = new JavaBeanIO<>();

    public static final JavaBeanIO<Character> CHAR = new JavaBeanIO<>();

    public static final JavaBeanIO<JsonElement> JSON_OBJECT = new JavaBeanIO<>(
            JsonObject:: create
    );

    public static final JavaBeanIO<JsonElement> JSON_ARRAY = new JavaBeanIO<>(
            JsonArray:: create
    );
    // TODO: value is JSON_ARRAY
}  
