package com.analysis.calculate.common.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ComponentScan({ "com.analysis.calculate" })
@Import({ ConfigProperties.class })
public class RedisConfiguration {

    @Value("${redis.hostname}")
    private String redisHostName;

    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.port}")
    private Integer redisPort;

    @Bean(name="redisTemplate")
    public RedisTemplate<String, String> redisTopologyTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);

        return template;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(redisHostName);
        connectionFactory.setPort(redisPort);
        connectionFactory.setPassword(redisPassword);
        return connectionFactory;
    }

}
