package com.algaworks.algafoodapi.api.dto.request;

import com.algaworks.algafoodapi.core.validation.Multiple;
import com.algaworks.algafoodapi.domain.model.Address;
import com.algaworks.algafoodapi.domain.model.Cuisine;
import com.algaworks.algafoodapi.domain.model.Restaurant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class RestaurantRequestDTO {

    @NotBlank
    private final String name;

    @NotNull
    @Multiple(message = "Invalid multiple", number = 5)
    @PositiveOrZero
    private final BigDecimal deliveryFee;

    @Positive
    private final Long cuisineId;

    @Valid
    @NotNull
    private final AddressRequestDTO address;

    public Restaurant toRestaurant(Cuisine cuisine, Address address) {
        return Restaurant.builder()
                .name(this.getName())
                .deliveryFee(this.getDeliveryFee())
                .cuisine(cuisine)
                .address(address)
                .build();
    }

}