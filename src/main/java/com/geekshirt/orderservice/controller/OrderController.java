package com.geekshirt.orderservice.controller;

import com.geekshirt.orderservice.dto.OrderRequest;
import com.geekshirt.orderservice.dto.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class OrderController {

    @GetMapping("/order")
    public ResponseEntity<List<OrderResponse>> findAll(){
        List<OrderResponse> orderList = new ArrayList<OrderResponse>();

        OrderResponse response = new OrderResponse();
        response.setAccountId("32422");
        response.setOrderId("1");
        response.setStatus("PENDING");
        response.setTotalAmount(100.000);
        response.setTotalTax(10.00);
        response.setTransactionDate(new Date());

        OrderResponse response2 = new OrderResponse();
        response2.setAccountId("32422");
        response2.setOrderId("2");
        response2.setStatus("PENDING");
        response2.setTotalAmount(100.000);
        response2.setTotalTax(10.00);
        response2.setTransactionDate(new Date());

        orderList.add(response);
        orderList.add(response2);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponse> findById(@PathVariable String orderId){
        OrderResponse response = new OrderResponse();
        response.setAccountId("32422");
        response.setOrderId(orderId);
        response.setStatus("PENDING");
        response.setTotalAmount(100.000);
        response.setTotalTax(10.00);
        response.setTransactionDate(new Date());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/order/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest payload){
        OrderResponse response = new OrderResponse();
        response.setAccountId(payload.getAccountId());
        response.setOrderId("9999");
        response.setStatus("PENDING");
        response.setTotalAmount(100.000);
        response.setTotalTax(10.00);
        response.setTransactionDate(new Date());

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
