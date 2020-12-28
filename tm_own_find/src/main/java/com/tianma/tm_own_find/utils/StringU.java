package com.tianma.tm_own_find.utils;

/**
 * author  honglei92
 * date    2020/12/2
 */
public class StringU {
    public static boolean isAutoFull(String url) {
        String isAutoFull = getValueByName(url, "isAutoFull");
        return "1".equals(isAutoFull);
    }

    public static String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }
}
