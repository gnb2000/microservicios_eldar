package com.geekshirt.orderservice.entities;

import com.geekshirt.orderservice.util.OrderPaymentStatus;
import com.geekshirt.orderservice.util.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order extends CommonEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "STATUS")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "ACCOUNT_ID")
    private String accountId;

    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

    @Column(name = "TOTAL_TAX")
    private Double totalTax;

    @Column(name = "TOTAL_AMOUNT_TAX")
    private Double totalAmountTax;

    @Column(name = "PAYMENT_STATUS")
    @Enumerated(value = EnumType.STRING)
    private OrderPaymentStatus paymentStatus;

    @Column(name = "TRANSACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,  mappedBy = "order")
    //FetchType.lazy = Solo voy a llamar a la lista de detalles cuando se llame al metodo getDetails()
    //FetchType.EAGER = Se trae siempre a los hijos cuando obtengo la Order. Usar cuando tengo pocos registros
    private List<OrderDetail> details;
}
