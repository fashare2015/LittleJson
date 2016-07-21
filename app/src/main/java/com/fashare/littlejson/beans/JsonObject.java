package com.fashare.littlejson.beans;


import com.fashare.littlejson.JsonObjectParsable;
import com.fashare.littlejson.constant.JsonSyntax;
import com.fashare.littlejson.utils.JsonIOUtil;
import com.fashare.littlejson.utils.JsonTypeSwitcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 17:23
 * <br/><br/>
 * 由 map 存放的一系列 json 键值对 {@link JsonItem}
 */
public class JsonObject implements JsonObjectParsable {
    protected HashMap<String, Object> jsonMap = new HashMap<>();

    public HashMap<String, Object> getJsonMap() {
        return jsonMap;
    }

    public JsonObject(){}

    public JsonObject(List<JsonItem> jsonItems) {
        jsonItems.stream().forEach(this:: putItem);
    }

    // value 可能是基本类型, 也可能是嵌套的 JsonObject
    public void putItem(JsonItem jsonItem) {
        jsonMap.put(jsonItem.getKey(), jsonItem.getValue());
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
        return JsonIOUtil.surroundBy(
                getJsonItemList().stream()
                        .map(JsonItem:: toString)
                        .reduce(this:: mergeString)
                        .orElse("")
                , JsonSyntax.BRACE_BEGIN);
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
    public static class JsonItem implements Map.Entry<String, Object> {
        private String key;
        private Object value;

        public JsonItem(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public JsonItem(Object[] keyAndValue){
            this((String) keyAndValue[0], keyAndValue[1]);
        }

        public JsonItem(Map.Entry<String, Object> entry) {
            this(entry.getKey(), entry.getValue());
        }

        @Override
        public String toString() {
            return String.format("%s%s %s",
                    JsonTypeSwitcher.write(getKey()),
                    JsonSyntax.COLON,
                    JsonTypeSwitcher.write(getValue())  // 递归下去!!!
            );
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public Object setValue(Object o) {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }
}
