package vttp.ssf.assessment.eventmanagement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp.ssf.assessment.eventmanagement.models.Event;

public class RedisConfig {
    
    // Railway: REDIS_HOST
    @Value("${redis.host}")
    private String redisHost;

    // Railway: REDIS_PORT
    @Value("${redis.port}")
    private Integer redisPort;

    // Railway: REDIS_USERNAME
    @Value("${redis.username}")
    private String redisUsername;

    // Railway: REDIS_PASSWORD
    @Value("${redis.password}")
    private String redisPassword;

    @Bean
    public JedisConnectionFactory jedisConnFactory() {

        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration(redisHost, redisPort);

        if (redisUsername != null && !redisUsername.isEmpty()) {
            rsc.setUsername(redisUsername);
        }

        if (redisPassword != null && !redisPassword.isEmpty()) {
            rsc.setPassword(redisPassword);
        }

        JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisFac = new JedisConnectionFactory(rsc, jedisClient);
        jedisFac.afterPropertiesSet();

        return jedisFac;
    }

    @Bean
    public RedisTemplate<String, Event> redisEventTemplate() {
        RedisTemplate<String, Event> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        return redisTemplate;
    }
}
