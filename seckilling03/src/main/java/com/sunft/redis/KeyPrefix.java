package com.sunft.redis;

/**
 * Created by sunft on 2018/5/25.
 * 前缀,，为了防止key冲突
 */
public interface KeyPrefix {

    /**
     * 过期时间
     * @return
     */
    int expireSeconds();

    /**
     * 前缀
     * @return
     */
    String getPrefix();
}
