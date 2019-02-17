package com.hong2.ycdl.util;

import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

public class HongStringUtil {

    public static String getText(EditText e) {
        if (e == null) {
            return "";
        }

        return e.getText().toString();
    }
    public static boolean isBlank(String s) {
        if (s == null) {
            return true;
        }

        if (s.equals("")) {
            return true;
        }

        if (s.isEmpty()) {
            return true;
        }

        return false;
    }
}
