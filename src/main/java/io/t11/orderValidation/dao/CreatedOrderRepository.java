package io.t11.orderValidation.dao;

import io.t11.orderValidation.model.CreatedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatedOrderRepository extends JpaRepository<CreatedOrder,Long> {
}
