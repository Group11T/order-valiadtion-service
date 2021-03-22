package io.t11.orderValidation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group11.soap.api.order_validation.Order;
import io.t11.orderValidation.model.CreatedOrder;
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

    public OrderValidationPublisher(RedisTemplate<String,Order> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void publishValidOrder(CreatedOrder createdOrder){
        logger.info("Publishing: {}",createdOrder.getId()," to trade engine");
        redisTemplate.convertAndSend(validOrdersTopic().getTopic(),createdOrder);
    }

    @Override
    public void publishOrderToRegister(Order order) throws JsonProcessingException {
        logger.info("Publishing: {}",order.getOrderId()," to register");
        redisTemplate.convertAndSend(registerTopic().getTopic(),order);
    }

}
