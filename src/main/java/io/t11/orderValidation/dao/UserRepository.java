package io.t11.orderValidation.dao;

import io.t11.orderValidation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository <User, Long> {
    User findByEmailAddress(String email);
}
