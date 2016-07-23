//package com.fashare.littlejson.utils;
//
//
//import com.fashare.littlejson.beans.JsonElement;
//import com.fashare.littlejson.beans.JsonObject;
//
//import java.lang.reflect.Field;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * User: fashare(153614131@qq.com)
// * Date: 2016-06-25
// * Time: 23:57
// * <br/><br/>
// * 类的解析工具(通过反射)
// */
//public class JavaBeanIOUtil {
//
//    public static JsonElement parseJsonElement(Object obj) {
//        return Stream.of(getJsonItemList(obj))
////                .map(JsonObject:: new)
//                .map(jsonItems -> JsonElement.createJsonElementBy(jsonItems, obj))
//                .findFirst().orElse(null);
//    }
//
//    private static List<JsonObject.JsonObjectItem> getJsonItemList(Object obj) {
//        return Stream.of(obj.getClass().getDeclaredFields())    // getFields
//                .map(field -> getJsonItem(field, obj))
//                .filter(Objects:: nonNull)
//                .collect(Collectors.toList());
//    }
//
//    private static JsonObject.JsonObjectItem getJsonItem(Field field, Object fatherObj){
//        JsonObject.JsonObjectItem jsonObjectItem = null;
//        try {
//            String key = field.getName();
//            Object value = null;
//
//            boolean accessible = field.isAccessible();
//            field.setAccessible(true);
//            {
//                value = field.get(fatherObj);
////                if(!ClassUtil.isBaseDataType(value.getClass()))   // value不是基本类型,递归下去,得到jsonObject
////                    value = com.example.JsonParser.fromBean(value);
//                value = JsonTypeSwitcher.read(value);
////                value = value.getClass().newInstance();
//                // 直接递归下去, 由 JsonTypeSwitcher 判断是否基本类型
//                jsonObjectItem = new JsonObject.JsonObjectItem(key, value);
//            }
//            field.setAccessible(accessible);
//        }catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return jsonObjectItem;
//    }
//
//    public static <T> T newInstance(JsonElement jsonElement, Class<T> clazz) {
//        return newInstance(jsonElement.getJsonItemList(), clazz);
//    }
//
//    private static <T> T newInstance(List<? extends JsonElement.JsonItem> jsonObjectItems, Class<T> clazz) {
//        Object object = null;
//        try {
//            final Object fatherObj = object = clazz.newInstance();
//            jsonObjectItems.stream().forEach(jsonItem ->
//                    setField(clazz.getDeclaredFields(), fatherObj, jsonItem));
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return (T)object;
//    }
//
//    private static void setField(Field[] fields, Object fatherObj, final JsonElement.JsonItem jsonObjectItem) {
//        Stream.of(fields).filter(field -> field.getName().equals(jsonObjectItem.getKey()))
//                .forEach(field -> {
//                    try {
//                        Object value = jsonObjectItem.getValue();
//                        boolean accessible = field.isAccessible();
//
//                        field.setAccessible(true);
//                        {
////                            if(value instanceof JsonElement) // value不是基本类型, 递归下去
////                                value = JsonParser.toBean((JsonElement) value, field.getType());
//                            value = JsonTypeSwitcher.write(value, field.getType());
//                            // 直接递归下去, 由 JsonTypeSwitcher 判断是否基本类型
//                            field.set(fatherObj, value);
//                        }
//                        field.setAccessible(accessible);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                });
//    }
//}
