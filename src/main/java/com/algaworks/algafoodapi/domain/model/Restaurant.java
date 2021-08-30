package com.algaworks.algafoodapi.domain.model;

import com.algaworks.algafoodapi.api.dto.request.RestaurantRequestDTO;
import com.algaworks.algafoodapi.api.dto.response.RestaurantResponseDTO;
import com.algaworks.algafoodapi.core.validation.FreeDeliveryFeeContainsName;
import com.algaworks.algafoodapi.core.validation.Multiple;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FreeDeliveryFeeContainsName(fieldValue = "deliveryFee",
        descriptionField = "name", mandatoryDescription = "Frete Grátis")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Multiple(message = "Invalid multiple", number = 5)
    @PositiveOrZero
    @Column(nullable = false)
    private BigDecimal deliveryFee;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cuisine_id")
    private Cuisine cuisine;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updatedAt;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurants_payment_options",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_option_id"))
    private Set<PaymentOption> paymentOptions = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    public void updateRestaurantData(RestaurantRequestDTO restaurantRequest, Cuisine cuisine, Address address) {
        this.setName(restaurantRequest.getName());
        this.setCuisine(cuisine);
        this.setDeliveryFee(restaurantRequest.getDeliveryFee());
        this.setAddress(address);
    }

    public RestaurantResponseDTO toRestaurantResponseDTO() {
        return RestaurantResponseDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .deliveryFee(this.getDeliveryFee())
                .cuisine(this.getCuisine().toCuisineResponseDTO())
                .address(this.getAddress())
                .build();
    }

    public void addPaymentOption(PaymentOption payment) {
        this.getPaymentOptions().add(payment);
    }

    public void removePaymentOption(PaymentOption payment) {
        this.getPaymentOptions().remove(payment);
    }

}