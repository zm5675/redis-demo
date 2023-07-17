package com.sa.jedis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisConnectionFactory {
    private static final JedisPool JEDIS_POOL;

    static {
        // 配置连接池参数
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxWaitMillis(1000);
        // 创建连接词对象
        JEDIS_POOL = new JedisPool(poolConfig,"192.168.246.128",6379,1000);
    }
    public static Jedis getJedis(){
        return JEDIS_POOL.getResource();
    }
}
