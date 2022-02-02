package com.geekshirt.orderservice.config;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Getter
@EnableJpaAuditing //Intercepta
@Configuration
@PropertySource({"classpath:application.properties"})
public class OrderServiceConfig {

    @Value("${customerservice.url}")
    private String customerServiceUrl; //Obtenemos el link desde el application.properties del customer service

    @Value("${paymentservice.url}")
    private String paymentServiceUrl; //Obtenemos el link desde el application.properties del payment service

    @Value("${inventoryservice.url}")
    private String inventoryServiceUrl;

    @Qualifier("outbound")
    @Bean
    public Queue inboundShipmentOrder(){
        return new Queue("INBOUND_SHIPMENT_ORDER",false,false,false);
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
