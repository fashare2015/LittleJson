package com.example.utils;

import com.example.beans.JsonObject;
import com.example.constant.JsonSyntax;
import com.example.constant.ValueType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 18:32
 * <br/><br/>
 * JsonString 语法解析工具
 */
public class JsonSyntaxUtil {

    public static List<JsonObject.JsonItem> getJsonItemList(String jsonString) {
        return Stream.of(jsonString.trim())
                .map(str -> str.substring(1, str.length()-1))   // 提取花括号中的实体
                .flatMap(itemMap -> Stream.of(mySplitItemMap(itemMap))) // 按逗号分割
                .map(String:: trim) // 去掉首尾空格
                .map(itemStr -> mySplitItemStr(itemStr))    // item => "\"key\": value".按冒号分割
                .filter(strings -> strings.length == 2)
                .map(JsonSyntaxUtil:: getKeyAndValue)
                .filter(JsonSyntaxUtil:: isKeyAndValueNotNull) // 保留 key 和 value 都不为空的
                .map(JsonObject.JsonItem:: new)
                .collect(Collectors.toList());
    }

    private static String[] mySplitItemMap(String itemMap) {
        List<String> ans = new ArrayList<>();
        String item = "";
        int dep = 0;
        for(int i=0; i<itemMap.length(); i++){
            char ch = itemMap.charAt(i);
            if (dep == 0 && ch == JsonSyntax.SEPARATOR){
                ans.add(item);
                item = "";
                continue;
            }
            if(ch == JsonSyntax.BRACE_BEGIN) dep ++;
            if(ch == JsonSyntax.BRACE_END) dep --;
            item += ch;
        }
        ans.add(item);
        return ans.toArray(new String[0]);
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
                .map(ValueType:: getValue)
                .toArray();
    }

    private static boolean isKeyAndValueNotNull(Object[] objs) {
        return objs[0]!=null && objs[1]!=null;
    }
}