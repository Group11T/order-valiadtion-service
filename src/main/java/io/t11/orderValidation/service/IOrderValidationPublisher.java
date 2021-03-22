package io.t11.orderValidation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group11.soap.api.order_validation.Order;

public interface IOrderValidationPublisher {

    void publishValidOrder(Order order) throws JsonProcessingException;

    void publishOrderToRegister(Order order) throws JsonProcessingException;
}
