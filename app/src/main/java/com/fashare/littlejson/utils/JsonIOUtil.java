//package com.fashare.littlejson.utils;
//
//
//import com.fashare.littlejson.beans.JsonElement;
//import com.fashare.littlejson.beans.JsonObject;
//import com.fashare.littlejson.constant.JsonSyntax;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * User: fashare(153614131@qq.com)
// * Date: 2016-06-24
// * Time: 18:32
// * <br/><br/>
// * JsonString 语法解析、重组工具
// */
//public class JsonIOUtil {
//
//    public static JsonElement parseJsonElement(String jsonString) {
//        return Stream.of(getJsonItemList(jsonString))
////                .map(JsonElement:: new)
//                .map(jsonItems -> JsonElement.createJsonElementBy(jsonItems, jsonString.charAt(0)))
//                .findFirst().orElse(null);
//    }
//
//    private static List<JsonObject.JsonObjectItem> getJsonItemList(String jsonString) {
//        return Stream.of(jsonString.trim())
//                .map(str -> str.substring(1, str.length()-1))   // 提取花括号中的实体
//                .flatMap(itemMap -> Stream.of(mySplitItemMap(itemMap))) // 按逗号分割
//                .map(String:: trim) // 去掉首尾空格
//                .map(itemStr -> mySplitItemStr(itemStr))    // item => "\"key\": value".按冒号分割
//                .filter(strings -> strings.length == 2)
//                .map(JsonIOUtil:: getKeyAndValue)
//                .filter(JsonIOUtil:: isKeyAndValueNotNull) // 保留 key 和 value 都不为空的
//                .map(JsonObject.JsonObjectItem:: new)
//                .collect(Collectors.toList());
//    }
//
//    private static String[] mySplitItemMap(String itemMap) {
//        List<String> ans = new ArrayList<>();
//        String item = "";
//        int dep = 0;
//        for(int i=0; i<itemMap.length(); i++){
//            char ch = itemMap.charAt(i);
//            if (dep == 0 && ch == JsonSyntax.SEPARATOR){
//                ans.add(item);
//                item = "";
//                continue;
//            }
//            if(ch == JsonSyntax.BRACE_BEGIN) dep ++;
//            if(ch == JsonSyntax.BRACE_END) dep --;
//            item += ch;
//        }
//        ans.add(item);
//        return ans.toArray(new String[0]);
//    }
//
//    private static String[] mySplitItemStr(String itemStr) {
//        int i=0;
//        for(; i<itemStr.length(); i++)
//            if(itemStr.charAt(i) == JsonSyntax.COLON)
//                break;
//        return Stream.of(itemStr.substring(0, i), itemStr.substring(i + 1, itemStr.length()))
//                .collect(Collectors.toList())
//                .toArray(new String[0]);
//    }
//
//    private static Object[] getKeyAndValue(String[] strings) {
//        return Stream.of(strings)
//                .map(String:: trim)
//                .map(JsonTypeSwitcher::read)    // 递归下去
//                .toArray();
//    }
//
//    private static boolean isKeyAndValueNotNull(Object[] objs) {
//        return objs[0]!=null && objs[1]!=null;
//    }
//
//    public static String surroundBy(Object str, char surroundBeginWith){
//        return Stream.of(JsonSyntax.SURROUND_MODS)
//                .filter(mod -> mod[0] == surroundBeginWith)
//                .map(mod -> "" + mod[0] + str + mod[1])
//                .findFirst().orElse(str.toString());
//    }
//}
