package com.example.interfaces;

import com.example.beans.JsonObject;
import com.example.constant.JsonType;

import java.util.function.Function;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 18:06
 * <br/><br/>
 * 在 {@link JsonObject.JsonItem} {key: value}中, <br/>
 * 根据 key/value 的{@link JsonType}, 用 Reader, Writer 进行读写 <br/>
 * @param <T> 解析后的对象 {@link JsonType}定义的一些合法类型：JsonObject/int/double...
 * @param <O> 待解析的对象 jsonString/JavaBean
 */
public class IO<T, O> {
    public interface Reader<T, O> extends Function<O, T> {}
    public interface Writer<T, O> extends Function<T, O> {}

    protected Reader<T, O> reader = null;
    protected Writer<T, O> writer = null;

    public IO(Reader<T, O> reader, Writer<T, O> writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public IO(Reader<T, O> reader){
        this.reader = reader;
    }

    public Reader<T, O> getReader(){
        return reader;
    }

    public Writer<T, O> getWriter(){
        return writer;
    }
}
