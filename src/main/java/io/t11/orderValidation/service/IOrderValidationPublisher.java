package io.t11.orderValidation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group11.soap.api.order_validation.Order;
import io.t11.orderValidation.model.Stock;

public interface IOrderValidationPublisher {

    void publishValidOrder(Stock stock) throws JsonProcessingException;

    void publishOrderToRegister(Order order) throws JsonProcessingException;
}
