package com.fashare.littlejson;

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
 * 1.toJson(): JavaBean/JsonObject -> JsonString <br/>
 * 2.fromJson(): JsonString/JsonObject -> JavaBean <br/>
 * 3.parseJsonObject(): JavaBean/JsonString -> JsonObject <br/>
 */
public interface JsonParsable {

}
