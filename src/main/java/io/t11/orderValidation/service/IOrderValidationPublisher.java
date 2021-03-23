package io.t11.orderValidation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.t11.orderValidation.model.Order;

public interface IOrderValidationPublisher {

    void publishValidOrder(Order order) throws JsonProcessingException;

    void publishOrderToRegister(Order order) throws JsonProcessingException;
}
