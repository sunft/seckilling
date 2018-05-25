package com.sunft.redis;

/**
 * Created by sunft on 2018/5/25.
 * 抽象前缀类
 */
public abstract class BasePrefix implements KeyPrefix {

    //过期时间
    private int expireSeconds;

    //前缀
    private String prefix;

    /**
     * 0代表永不过期
     * @param prefix 前缀
     */
    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /**
     * 默认0代表永不过期
     * @return 过期时间
     */
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    /**
     * 为防止前缀重复,最前面加上类名
     * @return 前缀
     */
    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
