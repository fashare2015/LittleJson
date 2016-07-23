package com.fashare.littlejson.beans;

import com.fashare.littlejson.JsonParser;
import com.fashare.littlejson.constant.JsonSyntax;
import com.fashare.littlejson.utils.JsonTypeSwitcher;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 17:23
 * <br/><br/>
 * 由 map 存放的一系列 json 键值对 {@link JsonObject.JsonObjectItem}
 */
public class JsonArray extends JsonElement{
    public static final String TAG = "JsonArray";
    public static final char JSON_SURROUNDING_CHAR = JsonSyntax.BRACKET_BEGIN;

    private List<JsonArrayItem> jsonArrayItems = new ArrayList<>();

    public JsonArray() {
        super();
    }

    public JsonArray(List<? extends JsonItem> jsonItems) {
        this(jsonItems, new StrIOUtil(), new BeanIOUtil());
//        System.out.println(TAG + " new JsonArray(jsonItems): " + jsonItems);
    }

    public JsonArray(List<? extends JsonItem> jsonItems, JsonArray.StrIOUtil strIOUtil, JsonArray.BeanIOUtil beanIOUtil) {
        super(jsonItems, strIOUtil, beanIOUtil);
        jsonItems.forEach(this:: putItem);  // TODO JsonElement.jsonItems 是空的 ???
    }

    private void putItem(JsonItem jsonArrayItem){
        jsonArrayItems.add((JsonArrayItem) jsonArrayItem);
    }

    public static JsonElement create(String jsonString) {
        return new StrIOUtil().parseJsonElement(jsonString);
    }

    public static JsonElement create(Object bean) {
//        System.out.println(TAG + " new JsonArray(bean): " + new Gson().toJson(bean));
        return new BeanIOUtil().parseJsonElement(bean);
    }

    @Override
    protected char getJsonSurroundingChar() {
        return JSON_SURROUNDING_CHAR;
    }

    /**
     * User: fashare(153614131@qq.com)
     * Date: 2016-06-24
     * Time: 20:04
     * <br/><br/>
     * JsonArray 的每一项, {value}
     * 可以是 JsonElement, 也可以是 int、String、double 等
     */
    public static class JsonArrayItem implements JsonItem{
        private static final String TAG = "JsonArrayItem";
        private Object value;

        private JsonArrayItem(){}

        public JsonArrayItem(Object value){
            this.value = value;
        }

        @Override
        public String toString() {
            return value instanceof JsonElement? ((JsonElement) value).toStr():
                    value.toString();
        }

        @Deprecated
        public String getKey() {
            throw new UnsupportedOperationException(TAG + "getKey");
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
                    .map(JsonArray:: new)
                    .findFirst().orElse(null);
        }

        private static List<JsonArrayItem> getJsonItemList(String jsonString) {
            return Stream.of(jsonString.trim())
                    .map(str -> str.substring(1, str.length()-1))   // 提取花括号中的实体
                    .flatMap(itemMap -> Stream.of(JsonElement.StrIOUtil.mySplitItemMap(itemMap))) // 按逗号分割
                    .map(String:: trim) // 去掉首尾空格
//                    .map(itemStr -> mySplitItemStr(itemStr))    // item => "\"key\": value".按冒号分割
//                    .filter(strings -> strings.length == 2)
//                    .map(StrIOUtil:: getKeyAndValue)
                    .map(JsonTypeSwitcher:: read)
                    // JsonArrayItem 是一个 JsonElement 或者 int/doule/..., 递归下去
                    .filter(Objects:: nonNull) // value 不为空
                    .map(JsonArrayItem:: new)
                    .collect(Collectors.toList());
        }

        // --- toStr ---
    }

    public static class BeanIOUtil implements JsonElement.BeanIOUtil{
        // -- fromBean ---
        public JsonElement parseJsonElement(Object obj) {
            return Stream.of(obj)
                    .map(BeanIOUtil:: getJsonItemList)
                    .map(jsonArrayItems -> {
//                        System.out.println("before ---------" + jsonArrayItems);
                        return jsonArrayItems;
                    })
                    .map(JsonArray:: new)
                    .map(jsonArray -> {
//                        System.out.println("after ----------" + jsonArray);
                        return jsonArray;
                    })
                    .findFirst().orElse(null);
        }

        private static List<JsonArrayItem> getJsonItemList(Object obj) {
            return (List<JsonArrayItem>)Stream.of(obj)
                    .filter(o -> o instanceof List) // 判断 obj 是 List 类型
                    .map(o -> (List)o)  // 转换成 List
                    .flatMap(list -> list.stream())
                    .map(JsonTypeSwitcher:: read)   // 直接递归下去, 由 JsonTypeSwitcher 判断是否基本类型
                    .filter(Objects:: nonNull)
                    .map(JsonArrayItem:: new)
                    .collect(Collectors.toList());
        }

        // --- toBean ---
        public <T> T newInstance(JsonElement jsonElement, Class<T> clazz) {
            return newInstance(jsonElement.getJsonItemList(), clazz);
        }

        private static <T> T newInstance(List<? extends JsonItem> jsonArrayItems, Class<T> clazz) {
            Object object = null;
                System.out.println("BeanIOUtil: newInstance:" + clazz);
                if(List.class.isAssignableFrom(clazz)){ // JsonArray
                    // TODO 获取 paramType
//                    Type paramType = ReflectUtils.getSuperclassTypeParameter(clazz);    // 获取 <T> 的类型
                    Type paramType = JsonParser.getParameterizedType().getActualTypeArguments()[0];
//                    Class rawType = JsonParser.getJavaBeanClazz();
                    System.out.println("paramType: " + paramType);
                    System.out.println(paramType instanceof Class);
//                    System.out.println("rawType: " + rawType);

                    List list = null;
                    try {
                        list = (List)clazz.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    list.addAll(jsonArrayItems.stream()
                            .map(jsonArrayItem -> jsonArrayItem.getValue())
                            .map(value -> JsonTypeSwitcher.write(value, (Class<?>) paramType))
                            .collect(Collectors.toList())
                    );

                    object = list;

                }else {
                    // error
                }
            return (T)object;
        }
    }
}
