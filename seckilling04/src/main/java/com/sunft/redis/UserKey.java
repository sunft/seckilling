package com.sunft.redis;

/**
 * Created by sunft on 2018/5/25.
 * 用户模块的键
 */
public class UserKey extends BasePrefix {

    /**
     * 私有的构造方法,防止被实例化
     * @param prefix 前缀
     */
    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");

    public static UserKey getByName = new UserKey("name");

}
