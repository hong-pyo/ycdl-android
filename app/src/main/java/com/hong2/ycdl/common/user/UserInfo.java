package com.hong2.ycdl.common.user;

public class UserInfo {
    public static long idx;
    private static String nickName;

    public static long getIdx() {
        return idx;
    }

    public static void setIdx(long idx) {
        UserInfo.idx = idx;
    }

    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nickName) {
        UserInfo.nickName = nickName;
    }
}
