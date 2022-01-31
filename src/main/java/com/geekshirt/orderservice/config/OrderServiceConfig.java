package com.geekshirt.orderservice.config;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource({"classpath:application.properties"})
public class OrderServiceConfig {

    @Value("${customerservice.url}")
    private String customerServiceUrl; //Obtenemos el link desde el application.properties del customer service

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
