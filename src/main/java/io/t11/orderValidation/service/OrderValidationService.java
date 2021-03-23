package io.t11.orderValidation.service;

import com.group11.soap.api.order_validation.ValidateOrderRequest;
import io.t11.orderValidation.dao.StockRepository;
import io.t11.orderValidation.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderValidationService implements IOrderValidationService{

    @Autowired
    StockRepository stockRepository;

    public OrderValidationService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public boolean validateOrder(final ValidateOrderRequest orderRequest) {
        if (orderRequest.getSide().equals("Buy")){
            return buyOrderValidate(orderRequest);
        }else if (orderRequest.getSide().equals("Sell")){
            return sellOrderValidate(orderRequest);
        }
        return  false;
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
    public Stock updateOrderValidity(Long id, String status) {
        Optional<Stock> stock = stockRepository.findById(id);
        stock.ifPresent(order->{
            order.setValidationStatus(status);
            stockRepository.save(order);
        });
        return stock.get();
    }
}
