package com.example.beans;

import com.example.constant.JsonSyntax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.util.Pair;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 17:23
 * <br/><br/>
 * 由 map 存放的一系列 json 键值对 {@link JsonItem}
 */
public class JsonArray {
    protected HashMap<String, Object> jsonMap = new HashMap<>();

    public HashMap<String, Object> getJsonMap() {
        return jsonMap;
    }

    public JsonArray(){}

    public JsonArray(List<JsonItem> jsonItems) {
        jsonItems.stream().forEach(this:: putItem);
    }

    // value 可能是基本类型, 也可能是嵌套的 JsonObject
    public void putItem(JsonItem jsonItem) {
        jsonMap.put((String) jsonItem.getKey(), jsonItem.getValue());
    }

    public String getString(String key){
        if(key != null)
            return (String)jsonMap.get(key);
        return "null";
    }

    public List<JsonItem> getJsonItemList(){
        return jsonMap.entrySet().stream()
                .map(JsonItem:: new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "" + JsonSyntax.BRACE_BEGIN
                + getJsonItemList().stream()
                        .map(JsonItem:: toString)
                        .reduce(this:: mergeString)
                        .orElse("")
                + JsonSyntax.BRACE_END;
    }

    private String mergeString(String a, String b) {
        return a + ("" + JsonSyntax.SEPARATOR+" ") + b;
    }

    public Object getObject(String key) {
        return key!=null? jsonMap.get(key): new Object();
    }

    /**
     * User: fashare(153614131@qq.com)
     * Date: 2016-06-24
     * Time: 20:04
     * <br/><br/>
     * Json 键值对, {key: value}
     * 基本等同于 Map.Entry
     */
    public static class JsonItem extends Pair<String, Object> {

        public JsonItem(String key, Object value) {
            super(key, value);
        }

        public JsonItem(Object[] keyAndValue){
            this((String) keyAndValue[0], keyAndValue[1]);
        }

        public JsonItem(Map.Entry<String, Object> entry) {
            this(entry.getKey(), entry.getValue());
        }

        @Override
        public String toString() {
            return String.format("\"%s\"%s %s", getKey(), JsonSyntax.COLON, getValueString());
        }

        private String getValueString() {
            Object obj = getValue();
            return obj instanceof String? String.format("\"%s\"", obj): obj.toString();
            // 如果 obj instanceof JsonObject, 也会自动递归下去.
        }
    }
}
