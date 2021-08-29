package com.algaworks.algafoodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Column(name = "address_zip_code")
    private String zipCode;

    @Column(name = "address_street_name")
    private String streetName;

    @Column(name = "address_number")
    private String number;

    @Column(name = "address_complement")
    private String complement;

    @Column(name = "address_neighborhood")
    private String neighborhood;

    @JsonIgnoreProperties("hibernateLazyInitializer")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_city_id")
    private City city;

}