package io.t11.orderValidation.service;

import com.group11.soap.api.order_validation.Order;

public interface IOrderValidationPublisher {

    void publishValidOrder(Order order);
}
