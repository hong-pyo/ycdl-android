package com.hong2.ycdl.util;

import android.widget.EditText;

import java.util.List;

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

    public static String howSimilarity(String answer, String target) {
        List<Character> inputAnswer = UniCodeHandler.splitHangeulToConsonant(target);
        List<Character> originAnswer = UniCodeHandler.splitHangeulToConsonant(answer);
        int fail = 0;
        for (int i = 0; i<originAnswer.size(); i++) {
            if (i < inputAnswer.size() && inputAnswer.get(i).equals(originAnswer.get(i))) {
                continue;
            } else {
                fail++;
            }
        }

        if (fail == 0) {
            return "정답";
        } else {
            double percent = ((double) (originAnswer.size() - fail) / (double) originAnswer.size()) * 100;
            String percentString = String.format("%.2f", percent);
            return "틀렸습니다. 유사도 :" + percentString + "%";
        }
    }
}
