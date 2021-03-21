package io.t11.orderValidation.controller;

import com.group11.soap.api.order_validation.ObjectFactory;
import com.group11.soap.api.order_validation.Order;
import com.group11.soap.api.order_validation.ValidateOrderRequest;
import com.group11.soap.api.order_validation.ValidateOrderResponse;
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

    @Autowired
    OrderValidationService orderService;

    @Autowired
    IOrderValidationPublisher validOrderPublisher;

    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "ValidateOrderRequest")
    @ResponsePayload
    public ValidateOrderResponse validateOrder(@RequestPayload ValidateOrderRequest orderRequest){
        logger.info("Request received for order validation: " + orderRequest.getProduct());
        ObjectFactory objectFactory = new ObjectFactory();
        ValidateOrderResponse validateOrderResponse=objectFactory.createValidateOrderResponse();
        logger.info("Outside: ");
        if (orderService.validateOrder(orderRequest)){
            logger.info("inside: ");
            Order order=new Order();
            order.setQuantity(orderRequest.getQuantity());
            order.setPrice(orderRequest.getPrice());
            order.setProduct(orderRequest.getProduct());
            order.setSide(orderRequest.getSide());
            validateOrderResponse.setOrder(order);
            validateOrderResponse.setStatus("Order successful");
            validOrderPublisher.publishValidOrder(order);

        }else {
            validateOrderResponse.setOrder(null);
            validateOrderResponse.setStatus("Order failed");
        }
        return validateOrderResponse;
    }

}
