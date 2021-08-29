package com.algaworks.algafoodapi.api.dto.response;

import com.algaworks.algafoodapi.domain.model.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class RestaurantResponseDTO {

    private final Long id;
    private final String name;
    private final BigDecimal deliveryFee;
    private final CuisineResponseDTO cuisine;
    private final Address address;

}