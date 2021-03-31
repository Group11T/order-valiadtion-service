package io.t11.orderValidation.service;


import com.group11.soap.api.order_validation.ValidateOrderRequest;
import io.t11.orderValidation.dao.OrderRepository;
import io.t11.orderValidation.model.Order;
import io.t11.orderValidation.dao.UserRepository;
import io.t11.orderValidation.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OrderValidationService implements IOrderValidationService{
    private Logger logger = LoggerFactory.getLogger(getClass());
    List<String> stocks = Arrays.asList("MSFT", "NFLX", "GOOGL", "AAPL", "TSLA", "IBM", "ORCL", "AMZN");
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;


    public OrderValidationService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean validateOrder(final ValidateOrderRequest orderRequest){

        if (stocks.contains(orderRequest.getProduct().toUpperCase())) {
            if (orderRequest.getSide().equalsIgnoreCase("Buy")) {
                logger.info("about time "+buyOrderValidate(orderRequest));
                return buyOrderValidate(orderRequest);
            } else if (orderRequest.getSide().equals("Sell")) {
                return sellOrderValidate(orderRequest);
            }
            return false;
        }
        return false;
    }

    boolean buyOrderValidate(ValidateOrderRequest orderRequest){
       if(userRepository.findById(orderRequest.getUserId()).isPresent()){
          User user =  userRepository.findById(orderRequest.getUserId()).get();
          if(user.getBalance() >= (orderRequest.getPrice()*orderRequest.getQuantity())) {
              return checkBuyBidRange(orderRequest);
          }
        };
       return false;
    }

    boolean sellOrderValidate(ValidateOrderRequest orderRequest){
        if(userRepository.findById(orderRequest.getUserId()).isPresent()){
            return checkSellBidRange(orderRequest);
        };
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

    Boolean checkBuyBidRange(ValidateOrderRequest orderRequest){
        RestTemplate restTemplate = new RestTemplate();
        LinkedHashMap response1 = restTemplate.getForObject("https://exchange.matraining.com/md/IBM",LinkedHashMap.class);
        int value = (int) response1.get("BUY_LIMIT");
        double price = (double) response1.get("ASK_PRICE");
        int shift = (int) response1.get("MAX_PRICE_SHIFT");
        try {
            LinkedHashMap response2 = restTemplate.getForObject("https://exchange2.matraining.com/md/IBM",LinkedHashMap.class);
            int value2 = (int) response2.get("BUY_LIMIT");
            double price2 = (double) response2.get("ASK_PRICE");
            int shift2 = (int) response2.get("MAX_PRICE_SHIFT");
            if(orderRequest.getQuantity()<=(value+value2)) {
                return (validRange(orderRequest.getPrice(), price, shift) || validRange(orderRequest.getPrice(), price2, shift2));
            }
        }catch (Exception e) {
            if(orderRequest.getQuantity()<=(value)) {
                return (validRange(orderRequest.getPrice(), price, shift));
            }
        }

        return false;
    }

    Boolean checkSellBidRange(ValidateOrderRequest orderRequest){
        RestTemplate restTemplate = new RestTemplate();
         String url = ("https://exchange.matraining.com/md/"+orderRequest.getProduct().toUpperCase());
        String url2 = ("https://exchange2.matraining.com/md/"+orderRequest.getProduct().toUpperCase());
        LinkedHashMap response1 = restTemplate.getForObject(url,LinkedHashMap.class);
        int value = (int) response1.get("SELL_LIMIT");
        double price = (double) response1.get("ASK_PRICE");
        int shift = (int) response1.get("MAX_PRICE_SHIFT");
        try{
            LinkedHashMap response2 = restTemplate.getForObject(url2,LinkedHashMap.class);
            int value2 = (int) response2.get("SELL_LIMIT");
            double price2 = (double) response2.get("ASK_PRICE");
            int shift2 = (int) response2.get("MAX_PRICE_SHIFT");
            if(orderRequest.getQuantity()<=(value+value2)) {
                return (validRange(orderRequest.getPrice(), price, shift) || validRange(orderRequest.getPrice(), price2, shift2));
            }
        }catch (Exception e){
            if(orderRequest.getQuantity()<=value) {
                return (validRange(orderRequest.getPrice(), price, shift));
            }
        }

        return false;
    }

    private boolean validRange(double value, double price, int shift)  {
        return (value>=(price-shift) && value <= (price+shift));
    }
}
