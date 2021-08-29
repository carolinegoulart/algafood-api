package com.algaworks.algafoodapi.api.dto.request;

import com.algaworks.algafoodapi.domain.model.Address;
import com.algaworks.algafoodapi.domain.model.City;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@RequiredArgsConstructor
public class AddressRequestDTO {

    @NotBlank
    private final String zipCode;

    @NotBlank
    private final String streetName;

    @NotBlank
    private final String number;

    private final String complement;

    @NotBlank
    private final String neighborhood;

    @NotNull
    @Positive
    private final Long cityId;

    public Address toAddress(City city) {
        return Address.builder()
                .zipCode(this.getZipCode())
                .streetName(this.getStreetName())
                .number(this.getNumber())
                .complement(this.getComplement())
                .neighborhood(this.getNeighborhood())
                .city(city)
                .build();
    }

}