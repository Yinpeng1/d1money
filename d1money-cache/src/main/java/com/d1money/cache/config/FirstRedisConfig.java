package com.d1money.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "spring.redis")
public class FirstRedisConfig extends RedisConfig{
    private int dbIndex;
    private String host;
    private int port;
    private int timeout;
    @Primary
    @Bean
    public JedisConnectionFactory defaultRedisConnectionFactory() {
        return jedisConnectionFactory(dbIndex,host, port, timeout);
    }
    @Bean(name = "FirstRedisTemplate")
    public RedisTemplate defaultRedisTemplate() {
        return super.redisTemplate(defaultRedisConnectionFactory());
    }
    @Bean(name = "FirstStringRedisTemplate")
    public StringRedisTemplate defaultStringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(defaultRedisConnectionFactory());
        template.afterPropertiesSet();
        return template;
    }
    public int getDbIndex() {
        return dbIndex;
    }
    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public int getTimeout() {
        return timeout;
    }
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
