package io.t11.orderValidation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group11.soap.api.order_validation.Order;
import io.t11.orderValidation.model.CreatedOrder;

public interface IOrderValidationPublisher {

    void publishValidOrder(CreatedOrder createdOrder) throws JsonProcessingException;

    void publishOrderToRegister(Order order) throws JsonProcessingException;
}
