package io.t11.orderValidation.service;

import com.group11.soap.api.order_validation.ValidateOrderRequest;
import io.t11.orderValidation.model.Stock;

public interface IOrderValidationService {

     boolean validateOrder(ValidateOrderRequest orderRequest);

     Stock updateOrderValidity(Long id, String status);
}
