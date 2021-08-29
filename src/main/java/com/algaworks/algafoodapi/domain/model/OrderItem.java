package com.algaworks.algafoodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer quantity;
    private String details;

    @ManyToOne
    @JoinColumn(nullable = false, name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(nullable = false, name = "order_id")
    private Order order;

}