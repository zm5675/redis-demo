package com.hmdp.utils;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hmdp.entity.Shop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.hmdp.utils.RedisConstants.*;

@Slf4j
@Component
public class CacheClient {
    private final StringRedisTemplate stringRedisTemplate;
    // 创建线程池
    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    public CacheClient(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void set(String key, Object val, Long time, TimeUnit timeUnit) {
        // 将数据存入redis中
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(val), time, timeUnit);
    }

    public void setWithLogicalExpire(String key, Object val, Long time, TimeUnit timeUnit) {
        // 写入redis
        RedisData redisData = new RedisData();
        redisData.setData(val);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(timeUnit.toSeconds(time)));
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
    }

    public <R, ID> R queryWithPassThrough(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallBack, Long time, TimeUnit unit) {
        String key = keyPrefix + id;
        // 从redis中查询该数据
        String json = stringRedisTemplate.opsForValue().get(key);
        // 判断是否存在
        if (StringUtils.isNotBlank(json)) {
            // 存在，直接返回
            return JSONUtil.toBean(json, type);

        }
        if (json != null) {
            // 返回一个错误信息
            return null;
        }
        // 不存在，查询数据库,这个查询数据库的操作，由方法的调用者传递
        R r = dbFallBack.apply(id);
        if (r == null) {
            // 将空值写入redis，防止缓存穿透问题
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 不存在，返回错误
            return null;
        }
        // 存在，写入redis并返回
        this.set(key, r, time, unit);
        return r;
    }

    /**
     * 封装工具类，解决缓存击穿问题
     * 使用逻辑设置过期时间的方式来解决缓存击穿问题
     */
    public <R,ID> R queryWithLogicExpire(String keyPrefix,ID id,Class<R> type,Function<ID,R> dbFallBack,Long time,TimeUnit unit) {
        String key = keyPrefix + id;
        // 从redis中查询该数据
        String json = stringRedisTemplate.opsForValue().get(key);
        // 判断是否命中
        if (StringUtils.isBlank(json)) {
            // 不存在，直接返回
            return null;
        }
        // 命中，需要将Json数据反序列化为对象
        RedisData redisData = JSONUtil.toBean(json, RedisData.class);
        R r = JSONUtil.toBean((JSONObject) redisData.getData(), type);
        LocalDateTime expireTime = redisData.getExpireTime();
        // 判断是否过期
        if (expireTime.isAfter(LocalDateTime.now())){
            // 未过期，直接返回店铺信息
            return r;
        }
        // 过期，需要缓存重建
        // 获取互斥锁
        String lockKey = LOCK_SHOP_KEY + id;
        boolean isLock = tryLock(lockKey);
        // 判断锁是否获取成功
        if (isLock) {
            // 成功,开启独立线程，实现缓存重建
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    // 先查询数据，在写入redis中
                    R apply = dbFallBack.apply(id);
                    setWithLogicalExpire(key,apply,time,unit);
                    // 释放锁
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    unlock(lockKey);
                }
            });
        }
        // 返回过期的商铺信息
        return r;
    }

    /**
     * 获取锁
     *
     * @param key
     * @return
     */
    private boolean tryLock(String key) {
        Boolean flay = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flay);
    }

    /**
     * 释放锁
     */
    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }
}
