package com.geekshirt.orderservice.service;

import com.geekshirt.orderservice.client.CustomerServiceClient;
import com.geekshirt.orderservice.dao.JpaOrderDAO;
import com.geekshirt.orderservice.dto.AccountDto;
import com.geekshirt.orderservice.dto.OrderRequest;
import com.geekshirt.orderservice.dto.OrderResponse;
import com.geekshirt.orderservice.entities.Order;
import com.geekshirt.orderservice.entities.OrderDetail;
import com.geekshirt.orderservice.exception.AccountNotFoundException;
import com.geekshirt.orderservice.exception.OrderNotFoundException;
import com.geekshirt.orderservice.util.ExceptionMessagesEnum;
import com.geekshirt.orderservice.util.OrderStatus;
import com.geekshirt.orderservice.util.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private JpaOrderDAO jpaOrderDAO;

    @Transactional
    public Order createOrder(OrderRequest orderRequest){

        OrderValidator.validateOrder(orderRequest);

        AccountDto accountDto = customerServiceClient.findAccountById(orderRequest.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(ExceptionMessagesEnum.ACCOUNT_NOT_FOUND.getValue()));

        Order newOrder = initOrder(orderRequest);
        return jpaOrderDAO.save(newOrder);
    }

    private Order initOrder(OrderRequest orderRequest){
        Order orderObj = new Order();
        orderObj.setOrderId(UUID.randomUUID().toString());
        orderObj.setAccountId(orderRequest.getAccountId());
        orderObj.setStatus(OrderStatus.PENDING);

        List<OrderDetail> orderDetails = orderRequest.getItems().stream()
                .map(item -> OrderDetail.builder()
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .upc(item.getUpc())
                        .tax( (item.getQuantity() * item.getPrice()) * 0.16)
                        .order(orderObj).build())
                .collect(Collectors.toList());

        orderObj.setDetails(orderDetails);
        orderObj.setTotalAmount(orderDetails.stream().mapToDouble(OrderDetail::getPrice).sum());
        orderObj.setTotalTax(orderObj.getTotalAmount() * 0.16);
        orderObj.setTransactionDate(new Date());

        return orderObj;
    }

    public List<Order> findAll(){
        return jpaOrderDAO.findAll();
    }

    public Order findById(String orderId){
        return jpaOrderDAO.findByOrderId(orderId)
                .orElseThrow( () -> new OrderNotFoundException("Order not found"));
    }

    public Order findById(Long id){
        return jpaOrderDAO.findById(id)
                .orElseThrow( () -> new OrderNotFoundException("Order not found"));
    }

}
