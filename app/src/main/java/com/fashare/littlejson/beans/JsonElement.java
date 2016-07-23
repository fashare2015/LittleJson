package com.fashare.littlejson.beans;


import com.fashare.littlejson.JavaBeanParsable;
import com.fashare.littlejson.JsonElementParsable;
import com.fashare.littlejson.constant.JsonSyntax;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-06-24
 * Time: 17:23
 * <br/><br/>
 * 由 map 存放的一系列 json 键值对 {@link JsonObject.JsonObjectItem}
 */
public abstract class JsonElement implements JsonElementParsable {
    private List<? extends JsonItem> jsonItems;
    private StrIOUtil strIOUtil;
    private BeanIOUtil beanIOUtil;

    public List<? extends JsonItem> getJsonItemList(){
        return jsonItems;
    }

    public StrIOUtil getStrIOUtil() {
        return strIOUtil;
    }

    public BeanIOUtil getBeanIOUtil() {
        return beanIOUtil;
    }

    public JsonElement() {}

    private JsonElement(List<? extends JsonItem> jsonItems) {
        this(jsonItems, null, null);
    }

    public JsonElement(List<? extends JsonItem> jsonItems, StrIOUtil strIOUtil, BeanIOUtil beanIOUtil) {
        this.jsonItems = jsonItems;
        this.strIOUtil = strIOUtil;
        this.beanIOUtil = beanIOUtil;
    }

//    public static JsonElement createJsonElementBy(List<? extends JsonItem> jsonItems, char jsonSurroundingChar){
//        switch (jsonSurroundingChar) {
//            case JsonObject.JSON_SURROUNDING_CHAR:
//                return new JsonObject(jsonItems);
//            case JsonArray.JSON_SURROUNDING_CHAR:
//                return new JsonArray(jsonItems);
//            default:
//                throw new JsonSyntaxException("起始字符应为\'{\'或\'[\'"); // TODO JsonSyntaxException
//        }
//    }
//
//    public static JsonElement createJsonElementBy(List<? extends JsonItem> jsonItems, Object obj){
//        if(obj instanceof List)
//            return new JsonArray(jsonItems);
//        return new JsonObject(jsonItems);
//    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
//        return toStr();
    }

    protected abstract char getJsonSurroundingChar();

    public interface JsonItem {
        String getKey();
        Object getValue();
    }

    // --- JsonIO: interface from JsonElementParsable ---
    @Override
    public JsonElement fromStr(String jsonString) {
        return strIOUtil.parseJsonElement(jsonString);
    }

    @Override
    public String toStr() {
        return strIOUtil.toStr(this);
    }

    @Override
    public JsonElement fromBean(JavaBeanParsable javaBeanParsable) {
        return beanIOUtil.parseJsonElement(javaBeanParsable);
    }

    @Override
    public JavaBeanParsable toBean(Class<? extends JavaBeanParsable> clazz) {
        return beanIOUtil.newInstance(this, clazz);
    }

    // --- JsonIO: my interface ---
    public interface StrIOUtil{
        // --- fromStr ---
        JsonElement parseJsonElement(String jsonString);

        static String[] mySplitItemMap(String itemMap) {
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
                if(ch == JsonSyntax.BRACE_BEGIN || ch == JsonSyntax.BRACKET_BEGIN) dep ++;
                if(ch == JsonSyntax.BRACE_END || ch == JsonSyntax.BRACKET_END) dep --;
                item += ch;
            }
            ans.add(item);
            return ans.toArray(new String[0]);
        }

        // --- toStr ---
        default String toStr(JsonElement jsonElement){
            return surroundBy(
                    jsonElement.getJsonItemList().stream()
                            .map(JsonItem:: toString)
                            .reduce(StrIOUtil:: mergeString)
                            .orElse("")
                    , jsonElement.getJsonSurroundingChar());
        }

        static String surroundBy(Object str, char surroundBeginWith){
            return Stream.of(JsonSyntax.SURROUND_MODS)
                    .filter(mod -> mod[0] == surroundBeginWith)
                    .map(mod -> "" + mod[0] + str + mod[1])
                    .findFirst().orElse(str.toString());
        }

        static String mergeString(String a, String b) {
            return a + ("" + JsonSyntax.SEPARATOR + " ") + b;
        }
    }

    public interface BeanIOUtil{
        // --- fromBean ---
        JsonElement parseJsonElement(Object obj);

        // --- toBean ---
        <T> T newInstance(JsonElement jsonElement, Class<T> clazz);
    }
}
