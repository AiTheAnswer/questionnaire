package com.allen.questionnaire.util;

public class TextUtil {
    public static boolean isEmpty(String text) {
        if (null == text) {
            return true;
        }
        if ("".equals(text)) {
            return true;
        }
        return false;
    }
}
