package com.fashare.littlejson.interfaces;


import com.fashare.littlejson.constant.JsonType;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 18:06
 * <br/><br/>
 * key/value<br/>
 * jsonString 和 {@link JsonType}指定类 之间的 IO 转换<br/>
 * @param <T> jsonString 对应的 {@link JsonType}指定类
 */
public class JsonIO<T> extends IO<T, String>{
//    private static Reader<T, String> DEFAULT_READER = str -> (T)str;
//    private static Writer<T, String> DEFAULT_WRITER = T:: toString;
//TODO    static 引用泛型？

    public JsonIO(Reader<T, String> reader, Writer<T, String> writer) {
        super(reader, writer);
    }

    public JsonIO(Reader<T, String> reader) {
        this(reader, T:: toString);
    }
}
