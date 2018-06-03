package com.sunft.redis;

/**
 * 秒杀用户
 * Created by sunft on 2018/6/3.
 */
public class SecKillingUserKey extends BasePrefix {

    //两天
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public SecKillingUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SecKillingUserKey token = new SecKillingUserKey(TOKEN_EXPIRE, "tk");

}
