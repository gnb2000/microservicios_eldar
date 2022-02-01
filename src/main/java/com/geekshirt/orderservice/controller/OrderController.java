package com.geekshirt.orderservice.controller;

import com.geekshirt.orderservice.dto.OrderRequest;
import com.geekshirt.orderservice.dto.OrderResponse;
import com.geekshirt.orderservice.entities.Order;
import com.geekshirt.orderservice.service.OrderService;
import com.geekshirt.orderservice.util.EntityDtoConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EntityDtoConverter entityDtoConverter;

    @ApiOperation(value="Retrieve all existed orders", notes = "This operation returns all stored orders")
    @GetMapping("/order")
    public ResponseEntity<List<OrderResponse>> findAll(){
        List<Order> orders = orderService.findAll();
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDto(orders), HttpStatus.OK);
    }

    @ApiOperation(value="Retrieve an order based on ID", notes = "This operation returns an order by ID")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponse> findById(@PathVariable String orderId){
        Order o = orderService.findById(orderId);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDto(o),HttpStatus.OK);
    }

    @ApiOperation(value="Creates an order", notes = "This operation creates a new order")
    @PostMapping("/order/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest payload){
        Order o = orderService.createOrder(payload);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDto(o),HttpStatus.CREATED);
    }

    @ApiOperation(value="Retrieve an order based on ID", notes = "This operation returns an order by ID")
    @GetMapping("/order/generated/{orderId}")
    public ResponseEntity<OrderResponse> findByGeneratedId(@PathVariable Long id){
        Order o = orderService.findById(id);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDto(o),HttpStatus.OK);
    }


}
