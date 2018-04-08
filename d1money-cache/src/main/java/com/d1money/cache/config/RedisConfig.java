package com.d1money.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "spring.redis.pool")
public class RedisConfig {
    @Value("${spring.redis.pool.max-active}")
    private int redisPoolMaxActive;
    @Value("${spring.redis.pool.max-idle}")
    private int redisPoolMaxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private int redisPoolMinIdle;
    @Value("${spring.redis.pool.max-wait}")
    private int redisPoolMaxWait;
    private JedisPoolConfig poolCofig(int maxIdle, int minIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow) {
        JedisPoolConfig poolCofig = new JedisPoolConfig();
        poolCofig.setMaxIdle(maxIdle);
        poolCofig.setMinIdle(minIdle);
        poolCofig.setMaxTotal(maxTotal);
        poolCofig.setMaxWaitMillis(maxWaitMillis);
        poolCofig.setTestOnBorrow(testOnBorrow);
        return poolCofig;
    }
    /**
     * 创建连接
     * @param index  index
     * @param host   主机
     * @param port   端口
     * @param timeout  时间
     * @return 连接配置
     */
    JedisConnectionFactory jedisConnectionFactory(int index, String host, int port, int timeout){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setDatabase(index);
        factory.setHostName(host);
        factory.setPort(port);
        factory.setTimeout(timeout); //设置连接超时时间
        //testOnBorrow为true时，返回的连接是经过测试可用的
        factory.setPoolConfig(poolCofig(redisPoolMaxIdle,redisPoolMinIdle,redisPoolMaxActive,redisPoolMaxWait,true));
        System.out.println("redis config========="+host);
        return factory;
    }
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setEnableTransactionSupport(true);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    /**CacheManager的一些属性设置，过期的时间等*/
    @Bean
    public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate){
        RedisCacheManager rm = new RedisCacheManager(redisTemplate);
        rm.setDefaultExpiration(60);
        return rm;
    }
    public int getRedisPoolMaxActive() {
        return redisPoolMaxActive;
    }
    public void setRedisPoolMaxActive(int redisPoolMaxActive) {
        this.redisPoolMaxActive = redisPoolMaxActive;
    }
    public int getRedisPoolMaxIdle() {
        return redisPoolMaxIdle;
    }
    public void setRedisPoolMaxIdle(int redisPoolMaxIdle) {
        this.redisPoolMaxIdle = redisPoolMaxIdle;
    }
    public int getRedisPoolMinIdle() {
        return redisPoolMinIdle;
    }
    public void setRedisPoolMinIdle(int redisPoolMinIdle) {
        this.redisPoolMinIdle = redisPoolMinIdle;
    }
    public int getRedisPoolMaxWait() {
        return redisPoolMaxWait;
    }
    public void setRedisPoolMaxWait(int redisPoolMaxWait) {
        this.redisPoolMaxWait = redisPoolMaxWait;
    }
}
