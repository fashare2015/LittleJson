package com.fashare.littlejson;


import com.fashare.littlejson.beans.JsonElement;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-07-19
 * Time: 00:03
 * <br/><br/>
 * Json 解析接口(类似 Serializable 接口)<br/>
 * 待序列化的 JavaBean 需要实现这个接口<br/>
 * 与 JsonParser 完全对应, 可以简化 Json 解析库的使用<br/>
 * <br/>
 * 主要有四个函数: <br/>
 * 1.toStr() / fromStr(): JsonElement <-> JsonString <br/>
 * 2.toBean() / fromBean(): JsonElement <-> JavaBean <br/>
 */
public interface JsonElementParsable extends JsonParsable {
    default JsonElement fromStr(String jsonString){
        return JsonParser.parse(jsonString);
    }

    default String toStr() {
        return JsonParser.toJson(this);
    }

    default JsonElement fromBean(JavaBeanParsable javaBeanParsable){
        return JsonParser.parse(javaBeanParsable);
    }

    default JavaBeanParsable toBean(Class<? extends JavaBeanParsable> clazz) {
        return JsonParser.fromJson((JsonElement) this, clazz);
    }
}
