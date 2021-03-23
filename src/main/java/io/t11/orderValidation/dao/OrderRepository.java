package io.t11.orderValidation.dao;

import io.t11.orderValidation.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
