package com.example.utils;

import com.example.beans.JsonItem;
import com.example.constant.JsonSyntax;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 18:32
 */
public class JsonSyntaxUtil {

//    public static boolean checkSyntax(String jsonString) {
//        if(jsonString == null)  return false;
//        String tmpStr = jsonString.trim();
//        if(jsonString.equals(""))   return true;
//        return checkBegin(tmpStr) && checkEnd(tmpStr) && checkItems(tmpStr);
//    }
//
//    private static boolean checkBegin(String tmpStr) {
//        return tmpStr.length()>0 && tmpStr.charAt(0) == JsonSyntax.BRACE_BEGIN;
//    }
//
//    private static boolean checkEnd(String tmpStr) {
//        return tmpStr.charAt(tmpStr.length() - 1) == JsonSyntax.BRACE_END;
//    }

//    private static boolean checkItems(String tmpStr) {
//        String itemMap = tmpStr.substring(1, tmpStr.length()-1);
//        String[] items = itemMap.split(JsonSyntax.SEPARATOR + "");
//
//        for (String item: items) {
//            // item => "\"key\": value"
//            String[] keyAndValue = item.split(JsonSyntax.COLON + "");
////            System.out.println(item);
//
//            if(keyAndValue.length != 2) return false;
//            String key = keyAndValue[0].trim(), value = keyAndValue[1].trim();
////            System.out.println(key + " => " + value);
////            System.out.println(isString(key) + ", " + isString(value));
//            if(!isString(key) || !isString(value))  return false;
//        }
//        return true;
//    }
//
//    private static boolean isString(String string) {
//        int len = string.length();
//        return len >= 2
//            && string.substring(0, 1).equals("\"")
//            && string.substring(len-1, len).equals("\"");
//    }

//    public static List<JsonItem> getJsonItems(String jsonString) {
//        String itemMap = getInnerString(jsonString);
//        String[] items = itemMap.split(JsonSyntax.SEPARATOR + "");
//        List<JsonItem> itemList = new ArrayList<>();
//        for (String item: items) {  // item => "\"key\": value"
//            String[] keyAndValue = item.split(JsonSyntax.COLON + "");
//            itemList.add(new JsonItem(getInnerString(keyAndValue[0]), getInnerString(keyAndValue[1])));
//        }
//        return itemList;
//    }

    public static List<JsonItem> getJsonItems(String jsonString) {
        return Stream.of(jsonString.trim())
                .map(JsonSyntaxUtil:: getInnerString) // 提取花括号中的实体
                .flatMap(itemMap -> Stream.of(itemMap.split(JsonSyntax.SEPARATOR + "")))    // 按逗号分割
                .map(String:: trim) // 去掉首尾空格
                .map(itemStr -> itemStr.split(JsonSyntax.COLON + ""))   // item => "\"key\": value".按冒号分割
                .map(JsonSyntaxUtil:: getInnerString) // 提取引号中的实体
                .filter(JsonSyntaxUtil:: isKeyAndValueNotNull) // 保留 key 和 value 都不为空的
                .map(JsonItem:: new)
                .collect(Collectors.toList());
    }

    private static String[] getInnerString(String[] strings) {
        return Stream.of(strings).map(JsonSyntaxUtil::getInnerString)
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    private static String getInnerString(String string){    // 提取引号中的实体
        string = string.trim();
        return string.length()>=2? string.substring(1, string.length()-1).trim(): null;
    }

    public static boolean isKeyAndValueNotNull(String[] strings) {
        return strings.length==2 && strings[0]!=null && strings[1]!=null;
    }

    public static boolean checkSyntax(String jsonString) {
        // "{"用"\\"来转义
        String jsonRegex = "^\\{ *(\"[a-zA-Z0-9]+\" *: *\"[a-zA-Z0-9]*\")? *(, *\"[a-zA-Z0-9]+\" *: *\"[a-zA-Z0-9]*\")* *\\}";
        Pattern pattern = Pattern.compile(jsonRegex);
        return pattern.matcher(jsonString).matches();
    }
}