package com.fashare.littlejson;


import com.fashare.littlejson.beans.JsonObject;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-07-19
 * Time: 00:03
 * <br/><br/>
 * Json 解析接口(类似 Serializable 接口)<br/>
 * 待序列化的 JavaBean 需要实现这个接口<br/>
 * 与 JsonParser 完全对应, 可以简化 Json 解析库的使用<br/>
 * <br/>
 * 主要有三个函数: <br/>
 * 1.toJson(): JsonObject -> JsonString <br/>
 * 2.fromJson(): JsonObject -> JavaBean <br/>
 * 3.parseJsonObject(): JavaBean/JsonString -> JsonObject <br/>
 */
public interface JsonObjectParsable extends com.fashare.littlejson.JsonParsable {
    default JsonObject parse(String jsonString){
        return JsonParser.parse(jsonString);
    }

    default JsonObject parse(JavaBeanParsable javaBeanParsable){
        return JsonParser.parse(javaBeanParsable);
    }

    default String toJson() {
        return JsonParser.toJson(this);
    }

    default JavaBeanParsable fromJson(Class<? extends JavaBeanParsable> clazz) {
        return JsonParser.fromJson((JsonObject) this, clazz);
    }
}
