package com.hong2.ycdl.common.user;

public class UserInfo {
    public static long idx;
    private static String nickName;

    public static boolean checkManage() {
        if (UserInfo.idx == 967392860L) {
            return true;
        } else {
            return false;
        }
    }
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
