package com.example.constant;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-26
 * Time: 17:10
 * <br/>
 * 正则匹配模版
 */
public class RegexPattern {
    public static final String INT = "^-?[1-9]\\d*$";
    public static final String DOUBLE = "^-?[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
    public static final String BOOLEAN = "^(true|false)$";
    public static final String STRING = "^\".*\"$";
    public static final String CHAR = "^\'.?\'$";
    public static final String JSON_OBJECT = "^\\{.*\\}$";
    public static final String JSON_ARRAY = "^\\[.*\\]$";
}  