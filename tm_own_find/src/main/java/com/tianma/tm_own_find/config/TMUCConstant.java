package com.tianma.tm_own_find.config;

/**
 * 常量
 * Created by bin on 2018/1/16.
 */
public class TMUCConstant {

    public static class BundleParam {
        public static String SHOW_BACK_BUTTON = "tm_show_back_button";
    }

    public static class LoginType {
        public static int TYPE_LOGIN_BY_VALIDATE_CODE = 1;
        public static int TYPE_LOGIN_BY_PASSWORD = 2;
    }

    public static class ValidateCodeType {
        public static int TYPE_LOGIN = 1;
        public static int TYPE_FORGET_PASSWORD = 2;
        public static int TYPE_MODIFY_PASSWORD = 3;
        public static int TYPE_VALIDATE_ORIGINAL_MOBILE = 4;
        public static int TYPE_VALIDATE_NEW_MOBILE = 5;
    }
}
