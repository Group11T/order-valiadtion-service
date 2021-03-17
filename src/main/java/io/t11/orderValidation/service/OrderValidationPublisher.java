package io.t11.orderValidation.service;

import com.group11.soap.api.order_validation.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class OrderValidationPublisher implements IOrderValidationPublisher {

    private static Logger logger = LoggerFactory.getLogger((OrderValidationPublisher.class));

    @Autowired
    private ChannelTopic topic;

    @Autowired
    RedisTemplate<String, Order> redisTemplate;

    public OrderValidationPublisher(RedisTemplate<String,Order> redisTemplate, ChannelTopic topic) {
        this.topic = topic;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void publishValidOrder(Order order){
        logger.info("Publishing: {}",order.getProduct());
        redisTemplate.convertAndSend(topic.getTopic(),order);
    }

}
