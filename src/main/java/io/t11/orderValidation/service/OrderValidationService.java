package io.t11.orderValidation.service;

import com.group11.soap.api.order_validation.Order;
import com.group11.soap.api.order_validation.ValidateOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderValidationService implements IOrderValidationService{

    @Override
    public Boolean validateOrder(final ValidateOrderRequest orderRequest) {
//
        if (orderRequest.getSide().equals("Buy")){
            return buyOrderValidate(orderRequest);
        }else if (orderRequest.getSide().equals("Sell")){
            return sellOrderValidate(orderRequest);
        }
        return  false;
    }

    Boolean buyOrderValidate(ValidateOrderRequest orderRequest){
//        RestTemplate restTemplate = new RestTemplate();
//        String getBalanceUrl  = "http://localhost:8030/get-balance/id";
//        Order response = restTemplate.getForObject(getBalanceUrl , Order.class);
//        return ((orderRequest.getPrice() * orderRequest.getQuantity()) < response.getPrice());
        return true;

    }

    Boolean sellOrderValidate(ValidateOrderRequest orderRequest){
//        RestTemplate restTemplate = new RestTemplate();
//        String findPortfolioUrl  = "http://localhost:8080/find-portfolio/2";
//        ResponseEntity<String> response = restTemplate.getForEntity(findPortfolioUrl , String.class);
//        return response.hasBody();
        return false;
    }
}
