package com.example.utils;

import com.example.beans.JsonItem;
import com.example.constant.JsonSyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    private static boolean checkItems(String tmpStr) {
        String itemMap = tmpStr.substring(1, tmpStr.length()-1);
        String[] items = itemMap.split(JsonSyntax.SEPARATOR + "");

        for (String item: items) {
            // item => "\"key\": value"
            String[] keyAndValue = item.split(JsonSyntax.COLON + "");
//            System.out.println(item);

            if(keyAndValue.length != 2) return false;
            String key = keyAndValue[0].trim(), value = keyAndValue[1].trim();
//            System.out.println(key + " => " + value);
//            System.out.println(isString(key) + ", " + isString(value));
            if(!isString(key) || !isString(value))  return false;
        }
        return true;
    }

    private static boolean isString(String string) {
        int len = string.length();
        return len >= 2
            && string.substring(0, 1).equals("\"")
            && string.substring(len-1, len).equals("\"");
    }

    public static List<JsonItem> getItems(String jsonString) {
        String itemMap = getInnerString(jsonString);
        String[] items = itemMap.split(JsonSyntax.SEPARATOR + "");
        List<JsonItem> itemList = new ArrayList<>();
        for (String item: items) {  // item => "\"key\": value"
            String[] keyAndValue = item.split(JsonSyntax.COLON + "");
            itemList.add(new JsonItem(getInnerString(keyAndValue[0]), getInnerString(keyAndValue[1])));
        }
        return itemList;
    }

    private static String getInnerString(String string){
        return string.trim().substring(1, string.length()-1);
    }

    public static boolean checkSyntax(String jsonString) {
        // "{"用"\\"来转义
        String jsonRegex = "^\\{ *(\"[a-zA-Z0-9]+\" *: *\"[a-zA-Z0-9]*\")? *(, *\"[a-zA-Z0-9]+\" *: *\"[a-zA-Z0-9]*\")* *\\}";
        Pattern pattern = Pattern.compile(jsonRegex);
        return pattern.matcher(jsonString).matches();
    }
}