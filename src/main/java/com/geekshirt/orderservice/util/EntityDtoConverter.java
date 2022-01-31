package com.geekshirt.orderservice.util;

import com.geekshirt.orderservice.dto.OrderResponse;
import com.geekshirt.orderservice.entities.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public OrderResponse convertEntityToDto(Order order){
        return modelMapper.map(order,OrderResponse.class); //Convertir de order a orderResponse
    }

    public List<OrderResponse> convertEntityToDto(List<Order> orders){
        return orders.stream()
                .map(order -> this.convertEntityToDto(order))
                .collect(Collectors.toList()); //Recorre la lista de orders y por cada uno, lo convierte al dto y devuelve la lista convertida
    }
}
