package io.t11.orderValidation.dao;

import io.t11.orderValidation.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {
}
