package com.uchechukwu.store.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

        @Value("${spring.data.redis.host}")
        private String host;

        @Value("${spring.data.redis.username}")
        private String username;

        @Value("${spring.data.redis.password}")
        private String password;

        @Value("${spring.data.redis.port}")
        private int port;

        @Bean
        public LettuceConnectionFactory redisConnectionFactory() {

                var redisConfig = new RedisStandaloneConfiguration();

                redisConfig.setHostName(host);

                redisConfig.setPort(port);

                redisConfig.setPassword(password);

                // redisConfig.setUsername(username);

                var socketOptions = SocketOptions.builder()
                                .connectTimeout(Duration.ofSeconds(10))
                                .build();

                var clientOptions = ClientOptions.builder()
                                .autoReconnect(true)
                                .socketOptions(socketOptions)
                                .disconnectedBehavior(
                                                ClientOptions.DisconnectedBehavior.ACCEPT_COMMANDS)
                                .build();

                var clientConfig = LettuceClientConfiguration.builder()
                                .clientOptions(clientOptions)
                                .commandTimeout(Duration.ofSeconds(12))
                                .shutdownTimeout(Duration.ZERO)
                                // .useSsl()
                                .build();

                return new LettuceConnectionFactory(
                                redisConfig,
                                clientConfig);
        }

        @Bean
        public StringRedisTemplate stringRedisTemplate(
                        LettuceConnectionFactory connectionFactory) {

                return new StringRedisTemplate(connectionFactory);
        }

        @Bean
        public GenericJackson2JsonRedisSerializer redisSerializer(
                        ObjectMapper objectMapper) {

                System.out.println(
                                "========== CUSTOM REDIS SERIALIZER LOADED ==========");

                ObjectMapper redisMapper = objectMapper.copy();

                // Use NON_FINAL instead of deprecated EVERYTHING
                // activateDefaultTyping overload with 3 args (validator, typing, include)
                redisMapper.activateDefaultTyping(
                                LaissezFaireSubTypeValidator.instance,
                                ObjectMapper.DefaultTyping.NON_FINAL,
                                JsonTypeInfo.As.PROPERTY);

                return new GenericJackson2JsonRedisSerializer(redisMapper);
        }

        @Bean
        public ObjectMapper objectMapper() {
                var mapper = new ObjectMapper();

                // 1. Support Java 8 Date/Time types
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                return mapper;
        }

        @Bean
        public RedisTemplate<String, Object> redisTemplate(
                        LettuceConnectionFactory connectionFactory, ObjectMapper objectMapper,
                        GenericJackson2JsonRedisSerializer serializer) {

                RedisTemplate<String, Object> template = new RedisTemplate<>();

                template.setConnectionFactory(connectionFactory);

                template.setKeySerializer(
                                new StringRedisSerializer());

                template.setValueSerializer(
                                serializer);

                template.setHashKeySerializer(
                                new StringRedisSerializer());

                template.setHashValueSerializer(
                                serializer);
                template.setEnableTransactionSupport(true);

                template.afterPropertiesSet();

                return template;
        }

        @Bean
        public CacheManager cacheManager(
                        LettuceConnectionFactory connectionFactory,
                        GenericJackson2JsonRedisSerializer serializer) {

                var config = RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(10))
                                .disableCachingNullValues()
                                .serializeKeysWith(
                                                RedisSerializationContext.SerializationPair
                                                                .fromSerializer(
                                                                                new StringRedisSerializer()))
                                .serializeValuesWith(
                                                RedisSerializationContext.SerializationPair
                                                                .fromSerializer(serializer));

                return RedisCacheManager.builder(connectionFactory)
                                .cacheDefaults(config)
                                .transactionAware()
                                .build();
        }
}