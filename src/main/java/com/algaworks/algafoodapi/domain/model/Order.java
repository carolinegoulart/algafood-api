package com.algaworks.algafoodapi.domain.model;

import com.algaworks.algafoodapi.domain.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalPrice;
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "payment_id")
    private PaymentOption payment;

    @ManyToOne
    @JoinColumn(nullable = false, name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();

    @Embedded
    private Address address;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    private OffsetDateTime confirmedAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime deliveredAt;

}