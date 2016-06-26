package com.example.utils;

import java.util.regex.Pattern;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 17:10
 * <br/>
 * 正则匹配工具
 */
public class RegexUtil {
    public static boolean matchPattern(String regexPattern, String str) {
        return Pattern.compile(regexPattern).matcher(str).matches();
    }
}