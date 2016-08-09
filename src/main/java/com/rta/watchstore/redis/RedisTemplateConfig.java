package com.rta.watchstore.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisTemplateConfig {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> getStringRestTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setHashValueSerializer( new GenericToStringSerializer<Object>( Object.class ) );
        redisTemplate.setValueSerializer( new GenericToStringSerializer<Object>( Object.class ) );

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
