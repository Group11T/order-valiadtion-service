package io.t11.orderValidation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.t11.orderValidation.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class OrderValidationPublisher implements IOrderValidationPublisher {

    private static Logger logger = LoggerFactory.getLogger((OrderValidationPublisher.class));

    ChannelTopic validOrdersTopic() {
        return new ChannelTopic("valid-orders");
    }

    ChannelTopic registerTopic() {
        return new ChannelTopic("validationService");
    }

    @Autowired
    RedisTemplate<String, Order> redisTemplate;

    public OrderValidationPublisher(RedisTemplate<String, Order> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void publishValidOrder(Order order){
        logger.info("Publishing: {}", order.getId()," to trade engine");
        redisTemplate.convertAndSend(validOrdersTopic().getTopic(), order);
    }

    @Override
    public void publishOrderToRegister(Order order) throws JsonProcessingException {
        logger.info("Publishing: {}",order.getId()," to register");
        redisTemplate.convertAndSend(registerTopic().getTopic(),order);
    }

}
