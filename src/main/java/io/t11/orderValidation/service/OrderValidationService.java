package io.t11.orderValidation.service;

import com.group11.soap.api.order_validation.ValidateOrderRequest;
import io.t11.orderValidation.dao.OrderRepository;
import io.t11.orderValidation.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderValidationService implements IOrderValidationService{

    List<String> stocks = Arrays.asList("MSFT", "NFLX", "GOOGL", "AAPL", "TSLA", "IBM", "ORCL", "AMZN");
    @Autowired
    OrderRepository orderRepository;

    public OrderValidationService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean validateOrder(final ValidateOrderRequest orderRequest) {
        if (stocks.contains(orderRequest.getProduct().toUpperCase())) {
            if (orderRequest.getSide().equalsIgnoreCase("Buy")) {
                return buyOrderValidate(orderRequest);
            } else if (orderRequest.getSide().equals("Sell")) {
                return sellOrderValidate(orderRequest);
            }
            return false;
        }
        return false;
    }

    boolean buyOrderValidate(ValidateOrderRequest orderRequest){
//        RestTemplate restTemplate = new RestTemplate();
//        String getBalanceUrl  = "http://localhost:8030/get-balance/id";
//        Order response = restTemplate.getForObject(getBalanceUrl , Order.class);
//        return ((orderRequest.getPrice() * orderRequest.getQuantity()) < response.getPrice());
        return true;

    }

    boolean sellOrderValidate(ValidateOrderRequest orderRequest){
//        RestTemplate restTemplate = new RestTemplate();
//        String findPortfolioUrl  = "http://localhost:8080/find-portfolio/2";
//        ResponseEntity<String> response = restTemplate.getForEntity(findPortfolioUrl , String.class);
//        return response.hasBody();
        return false;
    }

    @Override
    public Order updateOrderValidity(Long id, String status) {
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(createdOrder->{
            createdOrder.setValidationStatus(status);
            orderRepository.save(createdOrder);
        });
        return order.get();
    }

    Boolean checkOrderBidRange(){
        RestTemplate restTemplate = new RestTemplate();
        String currentMarketBid  = "http://localhost:8080/find-portfolio/2";
        ResponseEntity<String> response = restTemplate.getForEntity(currentMarketBid , String.class);
        return response.hasBody();
    }
}
