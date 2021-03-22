package io.t11.orderValidation.service;

import com.group11.soap.api.order_validation.ValidateOrderRequest;
import io.t11.orderValidation.model.CreatedOrder;

public interface IOrderValidationService {

     boolean validateOrder(ValidateOrderRequest orderRequest);

     CreatedOrder updateOrderValidity(Long id, String status);
}
