package com.geekshirt.orderservice.service;

import com.geekshirt.orderservice.dto.OrderRequest;
import com.geekshirt.orderservice.dto.OrderResponse;
import com.geekshirt.orderservice.entities.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    public Order createOrder(OrderRequest orderRequest){
        Order response = new Order();
        response.setAccountId(orderRequest.getAccountId());
        response.setOrderId("9999");
        response.setStatus("PENDING");
        response.setTotalAmount(100.000);
        response.setTotalTax(10.00);
        response.setTransactionDate(new Date());
        return response;
    }

    public List<Order> findAll(){
        List<Order> orderList = new ArrayList<Order>();

        Order response = new Order();
        response.setAccountId("32422");
        response.setOrderId("1");
        response.setStatus("PENDING");
        response.setTotalAmount(100.000);
        response.setTotalTax(10.00);
        response.setTransactionDate(new Date());

        Order response2 = new Order();
        response2.setAccountId("32422");
        response2.setOrderId("2");
        response2.setStatus("PENDING");
        response2.setTotalAmount(100.000);
        response2.setTotalTax(10.00);
        response2.setTransactionDate(new Date());

        orderList.add(response);
        orderList.add(response2);

        return orderList;
    }

    public Order findById(String orderId){
        Order response = new Order();
        response.setAccountId("32422");
        response.setOrderId(orderId);
        response.setStatus("PENDING");
        response.setTotalAmount(100.000);
        response.setTotalTax(10.00);
        response.setTransactionDate(new Date());

        return response;
    }

}
