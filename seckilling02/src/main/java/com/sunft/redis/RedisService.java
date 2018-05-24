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
     * 注入RedisConfig
     */
    @Autowired
    private RedisConfig redisConfig;

    /**
     * 根据key读取数据
     * @param key 键
     * @param clazz 类类型
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;

        try{
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
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
    public <T> boolean set(String key, T value) {
        Jedis jedis = null;

        try{
            jedis = jedisPool.getResource();
            String str = FastJsonUtils.beanToString(value);
            jedis.set(key, str);
            return true;
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

    @Bean
    public JedisPool JedisPoolFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        //0代表连接第一个数据库
        JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
                redisConfig.getTimeout() * 1000, redisConfig.getPassword(), 0);
        return jp;
    }

}
