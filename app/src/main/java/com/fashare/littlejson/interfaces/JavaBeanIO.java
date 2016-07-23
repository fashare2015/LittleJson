package com.fashare.littlejson.interfaces;


import com.fashare.littlejson.beans.JsonElement;
import com.fashare.littlejson.constant.JsonType;

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

    private Class<?> beanClass; // JsonElement 要解析成的类型

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
        this.writer = t -> !(t instanceof JsonElement)? t:
                ((JsonElement)t).getBeanIOUtil().newInstance((JsonElement) t, beanClass);
                // 如果是 JsonElement, 递归下去
    }

    public JavaBeanIO(Reader<T, Object> reader, Writer<T, Object> writer) {
        super(reader, writer);
    }
}
