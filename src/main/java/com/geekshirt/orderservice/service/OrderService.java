package com.geekshirt.orderservice.service;

import com.geekshirt.orderservice.client.CustomerServiceClient;
import com.geekshirt.orderservice.dto.AccountDto;
import com.geekshirt.orderservice.dto.OrderRequest;
import com.geekshirt.orderservice.dto.OrderResponse;
import com.geekshirt.orderservice.entities.Order;
import com.geekshirt.orderservice.exception.AccountNotFoundException;
import com.geekshirt.orderservice.util.ExceptionMessagesEnum;
import com.geekshirt.orderservice.util.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private CustomerServiceClient customerServiceClient;

    public Order createOrder(OrderRequest orderRequest){

        OrderValidator.validateOrder(orderRequest);

        AccountDto accountDto = customerServiceClient.findAccountById(orderRequest.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(ExceptionMessagesEnum.ACCOUNT_NOT_FOUND.getValue()));

       /* AccountDto dummyAccount = customerServiceClient.createDummyAccount();
        //dummyAccount = customerServiceClient.createAccount(dummyAccount);
        dummyAccount = customerServiceClient.createAccountBody(dummyAccount);

        dummyAccount.getAddress().setZipCode("999");
        customerServiceClient.updateAccount(dummyAccount);

        //System.out.println(customerServiceClient.findAccountById(orderRequest.getAccountId()));

        customerServiceClient.deleteAccount(dummyAccount);*/

        Order response = new Order();
        response.setAccountId("1");
        response.setOrderId("9999");
       // response.setStatus("PENDING");
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
        //response.setStatus("PENDING");
        response.setTotalAmount(100.000);
        response.setTotalTax(10.00);
        response.setTransactionDate(new Date());

        Order response2 = new Order();
        response2.setAccountId("32422");
        response2.setOrderId("2");
      //  response2.setStatus("PENDING");
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
       // response.setStatus("PENDING");
        response.setTotalAmount(100.000);
        response.setTotalTax(10.00);
        response.setTransactionDate(new Date());

        return response;
    }

}
