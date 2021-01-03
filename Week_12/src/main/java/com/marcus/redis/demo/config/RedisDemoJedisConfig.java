package com.marcus.redis.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import redis.clients.jedis.Jedis;

/**
 * @author: MarcusJiang1306
 */

@Configuration
public class RedisDemoJedisConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public Jedis jedis() {
        return ((JedisConnection) redisConnectionFactory.getConnection()).getJedis();
    }

}
