package com.cup.ycode.commons.utils;

import java.util.Random;

public class VerifyCodeUtils {
    private static final String[] EMAIL_VERIFYCODE_CHAR = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static int emailVerifyCodeCharArrLen = 0;
    private static Random random = new Random();
    static {
        emailVerifyCodeCharArrLen = EMAIL_VERIFYCODE_CHAR.length;
    }

    /**
     * 生成邮箱验证码
     * @return  邮箱验证码
     */
    public static String createEmailVerifyCode(){
        String s = "";
        for(int i=0;i<6;i++){
            s += EMAIL_VERIFYCODE_CHAR[random.nextInt(emailVerifyCodeCharArrLen)];
        }
        return s;
    }
}
