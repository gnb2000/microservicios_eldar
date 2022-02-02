package com.geekshirt.orderservice.repositories;

import com.geekshirt.orderservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findOrdersByAccountId(String accountId);
    Order findOrderByOrderId(String orderId);
}
