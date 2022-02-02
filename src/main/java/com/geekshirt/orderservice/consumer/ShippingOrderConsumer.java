package com.geekshirt.orderservice.consumer;

import com.geekshirt.orderservice.dto.ShipmentOrderResponse;
import com.geekshirt.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShippingOrderConsumer {

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = "OUTBOUND_SHIPMENT_ORDER") //La aplicacion va a estar escuchando la cola y cuando reciba una queue, ejecuta este metodo
    public void receive(final ShipmentOrderResponse in){
        log.debug(" [x] Received shipment Information: {}",in);
        orderService.updateShipmentOrder(in);


    }


}
