package io.t11.orderValidation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group11.soap.api.order_validation.ObjectFactory;
import com.group11.soap.api.order_validation.ValidateOrderRequest;
import com.group11.soap.api.order_validation.ValidateOrderResponse;
import io.t11.orderValidation.model.CreatedOrder;
import io.t11.orderValidation.service.IOrderValidationPublisher;
import io.t11.orderValidation.service.OrderValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WsOrderController {

    private static final String NAMESPACE_URI = "http://www.group11.com/soap/api/order-validation";
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final String successStatus="Order successful";
    private final String failedStatus="Order failed";

    @Autowired
    OrderValidationService orderValidationService;

    @Autowired
    IOrderValidationPublisher orderPublisher;

    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "ValidateOrderRequest")
    @ResponsePayload
    public ValidateOrderResponse validateOrder(@RequestPayload ValidateOrderRequest orderRequest) throws JsonProcessingException {
        logger.info("Request received for order validation: " + orderRequest.getOrderId());
        ObjectFactory objectFactory = new ObjectFactory();
        ValidateOrderResponse validateOrderResponse=objectFactory.createValidateOrderResponse();

        if (orderValidationService.validateOrder(orderRequest)){

            CreatedOrder order = new CreatedOrder();
            order.setId(orderRequest.getOrderId());
            order.setQuantity(orderRequest.getQuantity());
            order.setPrice(orderRequest.getPrice());
            order.setProduct(orderRequest.getProduct());
            order.setSide(orderRequest.getSide());
            order.setValidationStatus(successStatus);
            orderPublisher.publishValidOrder(order);

            validateOrderResponse.setStatus(successStatus);
        }else {
            validateOrderResponse.setStatus(failedStatus);
        }

        logger.info("updating order status",orderRequest.getOrderId());
        orderValidationService.updateOrderValidity(orderRequest.getOrderId(),validateOrderResponse.getStatus());
        return validateOrderResponse;
    }

}
