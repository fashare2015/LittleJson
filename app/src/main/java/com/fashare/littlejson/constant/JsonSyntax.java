package com.fashare.littlejson.constant;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 18:22
 * <br/><br/>
 * Json 语法常量
 */
public class JsonSyntax {


    // Json 首尾
    public static final char BRACE_BEGIN = '{';
    public static final char BRACE_END = '}';
    public static final char BRACKET_BEGIN = '[';
    public static final char BRACKET_END = ']';

    // value 首尾
    public static final char DOUBLE_QUOTE = '\"';
    public static final char SINGLE_QUOTE = '\'';

    // Json 分隔符
    public static final char COLON = ':';
    public static final char SEPARATOR = ',';

    // Json 重组方式: value -> {value} or [value] or "value" or 'value'
//    public static final int MOD_BARCE = 0;
//    public static final int MOD_BRACKET = 1;
//    public static final int MOD_DOUBLE_QUOTE = 2;
//    public static final int MOD_SINGLE_QUOTE = 3;

    public static final char[][] SURROUND_MODS = {
            {JsonSyntax.BRACE_BEGIN, JsonSyntax.BRACE_END},
            {JsonSyntax.BRACKET_BEGIN, JsonSyntax.BRACKET_END},
            {JsonSyntax.DOUBLE_QUOTE, JsonSyntax.DOUBLE_QUOTE},
            {JsonSyntax.SINGLE_QUOTE, JsonSyntax.SINGLE_QUOTE}
    };

}
