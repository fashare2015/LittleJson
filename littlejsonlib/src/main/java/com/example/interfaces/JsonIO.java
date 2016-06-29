package com.example.interfaces;

import com.example.beans.JsonObject;

import java.util.function.Function;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 18:06
 * <br/><br/>
 * 在 {@link JsonObject.JsonItem} {key: value}中, <br/>
 * 根据 key/value 的不同类型, 用 Reader 作不同的解析 <br/>
 * @param <T> key/value 对应的 JavaBean 的类型
 */
public class JsonIO<T> {
    public interface Reader<T> extends Function<String, T> {}
    public interface Writer<T> extends Function<T, String> {}

    private Reader<T> DEFAULT_READER = str -> (T)str;
    private Writer<T> DEFAULT_WRITER = t -> t.toString();

    private Reader<T> reader = DEFAULT_READER;
    private Writer<T> writer = DEFAULT_WRITER;

    public JsonIO(Reader<T> reader, Writer<T> writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public JsonIO(Reader<T> reader){
        this.reader = reader;
    }

//    public JsonIO(Writer<T> writer){
//        this.writer = writer;
//    }

    public Reader<T> getReader(){
        return reader;
    }

    public Writer<T> getWriter(){
        return writer;
    }
}
