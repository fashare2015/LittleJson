package com.fashare.littlejson.beans;


import com.fashare.littlejson.constant.JsonSyntax;
import com.fashare.littlejson.utils.JsonTypeSwitcher;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 17:23
 * <br/><br/>
 * 由 map 存放的一系列 json 键值对 {@link JsonObjectItem}
 */
public class JsonObject extends JsonElement {
    public static final char JSON_SURROUNDING_CHAR = JsonSyntax.BRACE_BEGIN;
    private static final Object TAG = "JsonObject";

    private HashMap<String, Object> jsonMap = new HashMap<>();

    public JsonObject() {
        this(new ArrayList<>());
    }

    public JsonObject(List<? extends JsonItem> jsonItems) {
        this(jsonItems, new StrIOUtil(), new BeanIOUtil());
    }

    public JsonObject(List<? extends JsonItem> jsonItems, JsonObject.StrIOUtil strIOUtil, JsonObject.BeanIOUtil beanIOUtil) {
        super(jsonItems, strIOUtil, beanIOUtil);
        if(jsonItems != null){
            jsonItems.forEach(this::putItem);
        }else
            System.out.println(TAG + "new JsonObject(jsonItems): jsonItems is null");
    }

    protected void putItem(JsonItem jsonObjectItem) {
//        System.out.println(TAG + String.format(" putItem(%s, %s)",
//                jsonObjectItem.getKey(), jsonObjectItem.getValue()));
        jsonMap.put(jsonObjectItem.getKey(), jsonObjectItem.getValue());
    }

    public static JsonElement create(String jsonString) {
        return new StrIOUtil().parseJsonElement(jsonString);
    }

    public static JsonElement create(Object bean) {
//        System.out.println(TAG + " new JsonObject(bean): " + new Gson().toJson(bean));
        return new BeanIOUtil().parseJsonElement(bean);
    }

    @Override
    protected char getJsonSurroundingChar() {
        return JSON_SURROUNDING_CHAR;
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
    public static class JsonObjectItem implements JsonItem{
        private String key;
        private Object value;

        private JsonObjectItem(){}

        public JsonObjectItem(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public JsonObjectItem(Object[] keyAndValue){
            this((String) keyAndValue[0], keyAndValue[1]);
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
    }

    // --- JsonIO ---
    public static class StrIOUtil implements JsonElement.StrIOUtil{
        // --- fromStr ---
        public JsonElement parseJsonElement(String jsonString) {
            return Stream.of(getJsonItemList(jsonString))
                    .map(JsonObject:: new)
                    .findFirst().orElse(null);
        }

        private static List<JsonObjectItem> getJsonItemList(String jsonString) {
            return Stream.of(jsonString.trim())
                    .map(str -> str.substring(1, str.length()-1))   // 提取花括号中的实体
                    .flatMap(itemMap -> Stream.of(JsonElement.StrIOUtil.mySplitItemMap(itemMap))) // 按逗号分割
                    .map(String:: trim) // 去掉首尾空格
                    .map(itemStr -> mySplitItemStr(itemStr))    // item => "\"key\": value".按冒号分割
                    .filter(strings -> strings.length == 2)
                    .map(StrIOUtil:: getKeyAndValue)
                    .filter(StrIOUtil:: isKeyAndValueNotNull) // 保留 key 和 value 都不为空的
                    .map(JsonObjectItem:: new)
                    .collect(Collectors.toList());
        }

        private static String[] mySplitItemStr(String itemStr) {
            int i=0;
            for(; i<itemStr.length(); i++)
                if(itemStr.charAt(i) == JsonSyntax.COLON)
                    break;
            return Stream.of(itemStr.substring(0, i), itemStr.substring(i + 1, itemStr.length()))
                    .collect(Collectors.toList())
                    .toArray(new String[0]);
        }

        private static Object[] getKeyAndValue(String[] strings) {
            return Stream.of(strings)
                    .map(String:: trim)
                    .map(JsonTypeSwitcher::read)    // 递归下去
                    .toArray();
        }

        private static boolean isKeyAndValueNotNull(Object[] objs) {
            return objs[0]!=null && objs[1]!=null;
        }

        // --- toStr ---
    }

    public static class BeanIOUtil implements JsonElement.BeanIOUtil{
        // -- fromBean ---
        public JsonElement parseJsonElement(Object obj) {
            return Stream.of(getJsonItemList(obj))
                .map(JsonObject:: new)
//                    .map(jsonItems -> JsonElement.createJsonElementBy(jsonItems, obj))
                    .findFirst().orElse(null);
        }

        private static List<JsonObject.JsonObjectItem> getJsonItemList(Object obj) {
            return Stream.of(obj.getClass().getDeclaredFields())    // getFields
                    .map(field -> getJsonItem(field, obj))
                    .filter(Objects:: nonNull)
                    .collect(Collectors.toList());
        }

        private static JsonObject.JsonObjectItem getJsonItem(Field field, Object fatherObj){
            JsonObject.JsonObjectItem jsonObjectItem = null;
            try {
                String key = field.getName();
                Object value = null;

                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                {
                    value = field.get(fatherObj);
//                if(!ClassUtil.isBaseDataType(value.getClass()))   // value不是基本类型,递归下去,得到jsonObject
//                    value = com.example.JsonParser.fromBean(value);
                    value = JsonTypeSwitcher.read(value);
//                value = value.getClass().newInstance();
                    // 直接递归下去, 由 JsonTypeSwitcher 判断是否基本类型
                    jsonObjectItem = new JsonObject.JsonObjectItem(key, value);
                }
                field.setAccessible(accessible);
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return jsonObjectItem;
        }

        // --- toBean ---
        public <T> T newInstance(JsonElement jsonElement, Class<T> clazz) {
            return newInstance(jsonElement.getJsonItemList(), clazz);
        }

        private static <T> T newInstance(List<? extends JsonElement.JsonItem> jsonObjectItems, Class<T> clazz) {
            Object object = null;
            try {
                final Object fatherObj = object = clazz.newInstance();
                jsonObjectItems.stream().forEach(jsonItem ->
                        setField(clazz.getDeclaredFields(), fatherObj, jsonItem));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return (T)object;
        }

        private static void setField(Field[] fields, Object fatherObj, final JsonElement.JsonItem jsonObjectItem) {
            Stream.of(fields).filter(field -> field.getName().equals(jsonObjectItem.getKey()))
                    .forEach(field -> {
                        try {
                            Object value = jsonObjectItem.getValue();
                            boolean accessible = field.isAccessible();

                            field.setAccessible(true);
                            {
//                            if(value instanceof JsonElement) // value不是基本类型, 递归下去
//                                value = JsonParser.toBean((JsonElement) value, field.getType());
                                value = JsonTypeSwitcher.write(value, field.getType());
                                // 直接递归下去, 由 JsonTypeSwitcher 判断是否基本类型
                                field.set(fatherObj, value);
                            }
                            field.setAccessible(accessible);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
