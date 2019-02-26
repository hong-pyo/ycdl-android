package com.hong2.ycdl.util;

import com.google.gson.Gson;

public class HongGsonUtil {
    private static Gson gson = new Gson();
    public static String getGsonString(Object o){
        return gson.toJson(o);
    }

    public static <T> T fromJson(String response, Class<T> classOfT) {
        return gson.fromJson(response, classOfT);
    }
}
