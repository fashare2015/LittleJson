package com.example.interfaces;

import com.example.beans.JsonObject;
import com.example.constant.JsonType;
import com.example.utils.JavaBeanIOUtil;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 18:06
 * <br/><br/>
 * javaBean 和 {@link JsonType}指定类 之间的 IO 转换<br/>
 * @param <T> javaBean 对应的 {@link JsonType}指定类
 */
public class JavaBeanIO<T> extends IO<T, Object>{
//    private static Reader<T, Object> DEFAULT_READER = str -> (T)str;
//    private static Writer<T, Object> DEFAULT_WRITER = T:: toString;
//TODO    static 引用泛型？

    private Class<?> beanClass; // JsonObject 要解析成的类型

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public JavaBeanIO(){
        this(o -> (T)o);
    }

    public JavaBeanIO(Reader<T, Object> reader) {
        super(reader);
        this.writer = t -> !(t instanceof JsonObject)? t:
                JavaBeanIOUtil.newInstance((JsonObject) t, beanClass); // 如果是 JsonObject, 递归下去
    }

    public JavaBeanIO(Reader<T, Object> reader, Writer<T, Object> writer) {
        super(reader, writer);
    }
}
