package com.sunft.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by sunft on 2018/5/25.
 * MD5工具类
 */
public final class MD5Util {

    //该字符串可以自己定义
    private static final String SALT = "1a2b3c4d";

    /**
     * MD5加密方法
     * @param src 源字符串
     * @return 经过MD5加密后的字符串
     */
    public static String md5(final String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * 第二次MD5
     * @param inputPass 入参
     * @return 加密后的字符串
     */
    public static String inputPassToFormPass(final String inputPass) {
        String str = SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(final String formPass, final String salt) {
        String str = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(final String inputPass, final String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
//        System.out.println(inputPassToFormPass("123456"));//
//		System.out.println(formPassToDBPass(inputPassToFormPass("123456"), "1a2b3c4d"));
		System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));//
    }

}
