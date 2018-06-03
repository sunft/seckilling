package com.sunft.redis;

import com.sunft.util.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by sunft on 2018/5/24.
 * 该类负责提供Redis服务
 */
@Service
public class RedisService {

    /**
     * 注入Jedis连接池
     */
    @Autowired
    private JedisPool jedisPool;

    /**
     * 根据key读取数据,获取单个对象
     * @param prefix 前缀
     * @param key 键
     * @param clazz 类类型
     * @return 值
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;

        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = FastJsonUtils.stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 根据键设置值
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;

        try{
            jedis = jedisPool.getResource();
            String str = FastJsonUtils.beanToString(value);
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            int expireSeconds = prefix.expireSeconds();//获取过期时间

            if(expireSeconds <= 0) {
                jedis.set(realKey, str);
            } else {
                //设置过期时间和其他信息
                jedis.setex(realKey, expireSeconds, str);
            }

            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     * @param prefix 前缀
     * @param key 键
     * @return key存在,返回true;key不存在,返回false
     */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(key);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值,如果值是数值,加一后返回
     * @param prefix 前缀
     * @param key 键
     * @return
     */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }

    }

    /**
     * 减少值,如果值是数值,减一后返回
     * @param prefix 前缀
     * @param key 键
     * @return
     */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 将连接返回到连接池中
     * @param jedis
     */
    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }


}
