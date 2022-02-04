package com.geekshirt.orderservice;

import com.geekshirt.orderservice.entities.Order;
import com.geekshirt.orderservice.repositories.OrderRepository;
import com.geekshirt.orderservice.util.OrderStatus;
import com.geekshirt.orderservice.util.RepositoryDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup(){
        //Cada vez que se llama al metodo test, se ejecuta esto antes de esto
        orderRepository.save(RepositoryDataUtils.getMockNotPersistentOrder("943242343", OrderStatus.DELIVERED));
        orderRepository.save(RepositoryDataUtils.getMockNotPersistentOrder("943242343", OrderStatus.DELIVERED));
        orderRepository.save(RepositoryDataUtils.getMockNotPersistentOrder("943242343", OrderStatus.DELIVERED));
        orderRepository.save(RepositoryDataUtils.getMockNotPersistentOrder("1234", OrderStatus.DELIVERED));
        orderRepository.save(RepositoryDataUtils.getMockNotPersistentOrder("1234", OrderStatus.SHIPPED));
    }

    @Test
    public void shouldSaveOrderWhenSaveIsCalled(){
        Order order = RepositoryDataUtils.getMockNotPersistentOrder("943242343",OrderStatus.PENDING);

        Order newOrder = orderRepository.save(order);

        Assertions.assertNotNull(newOrder.getId());

        Order foundOrder = orderRepository.findOrderByOrderId(newOrder.getOrderId());

        Assertions.assertNotNull(foundOrder);
        Assertions.assertEquals(order.getAccountId(),foundOrder.getAccountId());
        Assertions.assertNotNull(foundOrder.getOrderId());
        Assertions.assertEquals(newOrder.getOrderId(),foundOrder.getOrderId());
        Assertions.assertNotNull(foundOrder.getPaymentStatus());
        Assertions.assertNotNull(foundOrder.getStatus());
        Assertions.assertNotNull(foundOrder.getTotalAmount());
        Assertions.assertNotNull(foundOrder.getTotalTax());
        Assertions.assertNotNull(foundOrder.getTotalAmountTax());
    }

    @Test
    public void shouldReturnAllOrdersWhenFindAllIsCalled(){
        List<Order> orders = orderRepository.findAll();
        Assertions.assertEquals(5,orders.size());
    }

    @Test
    public void shouldReturnAllOrdersByAccountIdWhenFindByAccountIdIsCalled(){
        List<Order> orders = orderRepository.findOrdersByAccountId("943242343");
        Assertions.assertEquals(3,orders.size());
    }

    @Test
    public void shouldReturnOrderWhenFindByOrderIdIsCalled(){
        List<Order> orders = orderRepository.findOrdersByAccountId("943242343");
        Order order = orders.get(0);

        Order orderById = orderRepository.findOrderByOrderId(order.getOrderId());
        Assertions.assertNotNull(orderById);
        Assertions.assertEquals(order.getOrderId(),orderById.getOrderId());
    }




}
