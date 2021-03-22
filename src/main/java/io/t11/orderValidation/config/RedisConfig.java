package io.t11.orderValidation.config;

import com.group11.soap.api.order_validation.Order;
import io.t11.orderValidation.service.IOrderValidationPublisher;
import io.t11.orderValidation.service.OrderValidationPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Bean
    RedisTemplate<String, Order> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Order> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    //

    @Bean
    @Primary
    IOrderValidationPublisher orderPublisher(){
        return new OrderValidationPublisher(redisTemplate(redisConnectionFactory));
    }

}
