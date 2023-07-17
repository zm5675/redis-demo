package com.hmdp.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
public class RedisIDWork {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    // 定义时间戳
    private static final long BEGIN_TIMESTAMP = 1672531200L;
    // 定义移位的常量
    private static final int COUNT_BITS = 32;
    public long nextId(String keyPrefix){
        // 1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowLocal = now.toEpochSecond(ZoneOffset.UTC);
        long timeStamp = nowLocal - BEGIN_TIMESTAMP;
        String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 2.生成序列号
        /**
         * 当前自增序列,保证每个业务的key在每天都是新的
         * 拼接当前时间
         */
        long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);
        stringRedisTemplate.expire("icr:" + keyPrefix + ":" + date,1, TimeUnit.DAYS);
        return timeStamp << COUNT_BITS | count;

        // 3.拼接返回
    }

    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.of(2023,1,1,0,0);
        long second = time.toEpochSecond(ZoneOffset.UTC); // 2023,1,1,0,0 的时间戳
        System.out.println(second); // 1672531200
    }
}
