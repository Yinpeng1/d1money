package com.d1money.cache.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisClient<T> {

    @Resource(name = "FirstRedisTemplate")
    private RedisTemplate<String, T> redisTemplate;

    @Resource(name = "FirstStringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    /**缓存字符串，缓存时间为秒*/
    public void set(String key, String value, long timeout){
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }
    /**缓存字符串，不过期*/
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }
    /**设置key立马过期*/
   public void set(String key){
       stringRedisTemplate.expire(key, 0, TimeUnit.SECONDS);
   }
    /**获取key的value*/
    public String get(String key){
       return stringRedisTemplate.opsForValue().get(key);
    }

   /**设置对象，缓存时间为秒*/
   public void setKeyObj(String key, T t, long timeout){
        redisTemplate.opsForValue().set(key, t, timeout, TimeUnit.SECONDS);
   }

    /**设置对象，不过期*/
    public void setKeyObj(String key, T t){
        redisTemplate.opsForValue().set(key, t);
    }

    /**设置对象，立马过期*/
    public void setKeyObj(String key){
        redisTemplate.expire(key, 0, TimeUnit.SECONDS);
    }

    /**获取对象key的value*/
    public T getObj(String key){
        return  redisTemplate.opsForValue().get(key);
    }
}
