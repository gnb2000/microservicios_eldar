package com.geekshirt.orderservice.dto;

import com.geekshirt.orderservice.entities.Order;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;

@Data
public class OrderDetailResponse {

    private Long id;
    private int quantity;
    private double price;
    private double tax;
    private String upc;

}
