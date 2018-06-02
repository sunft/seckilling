package com.sunft.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunft on 2018/5/27.
 * 校验工具类
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    /**
     * 校验手机号
     * @param src 字符串
     * @return 是否是手机号
     */
    public static boolean isMobile(String src) {

        if(StringUtils.isEmpty(src)) {
            return false;
        }

        Matcher m = mobile_pattern.matcher(src);
        return m.matches();

    }

//    public static void main(String[] args) {
//        System.out.println(isMobile("13432083020"));
//        System.out.println(isMobile("1343208302X"));
//    }

}
