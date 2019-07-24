package com.zwx.instalment.app.data.request.json;

import com.google.gson.GsonBuilder;

/**
 * @author sam
 * @version 1.0
 * @date 2018/4/13
 */
public class FromJsonUtils {
    public static BaseDataResponse fromJson(String json, Class clazz) {
        return new GsonBuilder()
                .registerTypeAdapter(BaseDataResponse.class, new JsonFormatParser(clazz))
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .create()
                .fromJson(json, BaseDataResponse.class);
    }
}
