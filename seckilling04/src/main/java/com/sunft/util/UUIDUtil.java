package com.sunft.util;

import java.util.UUID;

/**
 * UUID工具类
 * Created by sunft on 2018/6/3.
 */
public final class UUIDUtil {

    /**
     * 生成UUID
     * @return UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-","");
    }

}
