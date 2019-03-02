package com.hong2.ycdl.util;

import java.util.ArrayList;
import java.util.List;

public class UniCodeHandler {
    private static final int HANGEUL_BASE = 0xAC00;    // '가'
    private static final int HANGEUL_END = 0xD7AF;
    // 이하 cho, jung, jong은 계산 결과로 나온 자모에 대해 적용
    private static final int CHO_BASE = 0x1100;
    private static final int JUNG_BASE = 0x1161;
    private static final int JONG_BASE = (int)0x11A8 - 1;
    // 이하 ja, mo는 단독으로 입력된 자모에 대해 적용
    private static final int JA_BASE = 0x3131;
    private static final int MO_BASE = 0x314F;

    /* https://gist.github.com/thirdj/5333532
      초성 중성 종성 분리 하기

        유니코드 한글은 0xAC00 으로부터
        초성 19개, 중상21개, 종성28개로 이루어지고
        이들을 조합한 11,172개의 문자를 갖는다.

        한글코드의 값 = ((초성 * 21) + 중성) * 28 + 종성 + 0xAC00
        (0xAC00은 'ㄱ'의 코드값)

        따라서 다음과 같은 계산 식이 구해진다.
        유니코드 한글 문자 코드 값이 X일 때,

        초성 = ((X - 0xAC00) / 28) / 21
        중성 = ((X - 0xAC00) / 28) % 21
        종성 = (X - 0xAC00) % 28

        이 때 초성, 중성, 종성의 값은 각 소리 글자의 코드값이 아니라
        이들이 각각 몇 번째 문자인가를 나타내기 때문에 다음과 같이 다시 처리한다.

        초성문자코드 = 초성 + 0x1100 //('ㄱ')
        중성문자코드 = 중성 + 0x1161 // ('ㅏ')
        종성문자코드 = 종성 + 0x11A8 - 1 // (종성이 없는 경우가 있으므로 1을 뺌)

    */

    public static List<Character> splitHangeulToConsonant(String text) {

        List<Character> list = new ArrayList<>();

        for(char c : text.toCharArray()) {
            System.out.println(c + "|" + (int)c);
            if((c <= 10 && c <= 13) || c == 32) {
                list.add(c);
                continue;
            } else if (c >= JA_BASE && c <= JA_BASE + 36) {
                list.add(c);
                continue;
            } else if (c >= MO_BASE && c <= MO_BASE + 58) {
                list.add((char)0);
                continue;
            } else if (c >= HANGEUL_BASE && c <= HANGEUL_END){
                int choInt = (c - HANGEUL_BASE) / 28 / 21;
                int jungInt = ((c - HANGEUL_BASE) / 28) % 21;
                int jongInt = (c - HANGEUL_BASE) % 28;
                char cho = (char) (choInt + CHO_BASE);
                char jung = (char) (jungInt + JUNG_BASE);
                char jong = jongInt != 0 ? (char) (jongInt + JONG_BASE) : 0;

                list.add(cho);
                list.add(jung);
                list.add(jong);
            } else {
                list.add(c);
            }

        }
        return list;

    }
}
