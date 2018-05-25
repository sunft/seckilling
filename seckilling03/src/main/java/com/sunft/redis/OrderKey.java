package com.sunft.redis;

/**
 * Created by sunft on 2018/5/25.
 * 订单模块的键
 */
public class OrderKey extends BasePrefix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}
