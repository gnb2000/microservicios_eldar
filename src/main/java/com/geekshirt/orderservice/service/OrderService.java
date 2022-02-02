package com.geekshirt.orderservice.service;

import com.geekshirt.orderservice.client.CustomerServiceClient;
import com.geekshirt.orderservice.client.InventoryServiceClient;
import com.geekshirt.orderservice.dao.JpaOrderDAO;
import com.geekshirt.orderservice.dto.*;
import com.geekshirt.orderservice.entities.Order;
import com.geekshirt.orderservice.entities.OrderDetail;
import com.geekshirt.orderservice.exception.AccountNotFoundException;
import com.geekshirt.orderservice.exception.OrderNotFoundException;
import com.geekshirt.orderservice.exception.PaymentNotAcceptedException;
import com.geekshirt.orderservice.producer.ShippingOrderProducer;
import com.geekshirt.orderservice.repositories.OrderRepository;
import com.geekshirt.orderservice.util.ExceptionMessagesEnum;
import com.geekshirt.orderservice.util.OrderPaymentStatus;
import com.geekshirt.orderservice.util.OrderStatus;
import com.geekshirt.orderservice.util.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private JpaOrderDAO jpaOrderDAO;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentProcessorService paymentService;

    @Autowired
    private InventoryServiceClient inventoryClient;

    @Autowired
    private ShippingOrderProducer shippingOrderProducer;

    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Order createOrder(OrderRequest orderRequest) throws PaymentNotAcceptedException {

        OrderValidator.validateOrder(orderRequest);

        AccountDto accountDto = customerServiceClient.findAccountById(orderRequest.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(ExceptionMessagesEnum.ACCOUNT_NOT_FOUND.getValue()));

        Order newOrder = initOrder(orderRequest);

        Confirmation confirmation = paymentService.processPayment(newOrder, accountDto);

        log.info("Payment Confirmation: {}", confirmation);

        String paymentStatus = confirmation.getTransactionStatus();
        newOrder.setPaymentStatus(OrderPaymentStatus.valueOf(paymentStatus));

        if (paymentStatus.equals(OrderPaymentStatus.DENIED.name())) {
            newOrder.setStatus(OrderStatus.NA);
            orderRepository.save(newOrder);
            throw new PaymentNotAcceptedException("The Payment added to your account was not accepted, please verify.");
        }

        log.info("Updating Inventory: {}", orderRequest.getItems());
        inventoryClient.updateInventory(orderRequest.getItems());

        log.info("Sending request to shipping service: {}");
        shippingOrderProducer.send(newOrder.getOrderId(),accountDto);


        return orderRepository.save(newOrder);
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
        return orderRepository.findAll();
    }

    public Order findById(String orderId){
        Optional<Order> order = Optional.ofNullable(orderRepository.findOrderByOrderId(orderId));
        return order.orElseThrow( () -> new OrderNotFoundException("Order not found"));
    }

    public Order findById(Long id){
        return orderRepository.findById(id)
                .orElseThrow( () -> new OrderNotFoundException("Order not found"));
    }

    public List<Order> findOrdersByAccountId(String accountId){
        Optional<List<Order>> orders = Optional.ofNullable(orderRepository.findOrdersByAccountId(accountId));
        return orders
                .orElseThrow( () -> new OrderNotFoundException("Orders were not found"));
    }

    public void updateShipmentOrder(ShipmentOrderResponse response){

        try {
            Order order = this.findById(response.getOrderId());
            order.setStatus(OrderStatus.valueOf(response.getShippingStatus()));
            orderRepository.save(order);
        }

        catch (OrderNotFoundException e){
            log.info("The following order was not found: {} with tracking id: {}",response.getOrderId(),response.getTrackingId());

        }

        catch (Exception e) {
            log.info("An error occurred");
        }

    }

}
