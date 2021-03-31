package io.t11.orderValidation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group11.soap.api.order_validation.ValidateOrderRequest;
import io.t11.orderValidation.model.Order;

public interface IOrderValidationService {

     boolean validateOrder(ValidateOrderRequest orderRequest) throws JsonProcessingException;

     Order updateOrderValidity(Long id, String status);
}
