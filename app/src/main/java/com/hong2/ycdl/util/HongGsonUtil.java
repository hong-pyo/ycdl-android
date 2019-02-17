package com.hong2.ycdl.util;

import com.google.gson.Gson;

public class HongGsonUtil {
    public static String getGsonString(Object o){
        Gson gson = new Gson();
        return gson.toJson(o);
    }
}
