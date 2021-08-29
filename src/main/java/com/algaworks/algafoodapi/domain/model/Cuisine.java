package com.algaworks.algafoodapi.domain.model;

import com.algaworks.algafoodapi.api.dto.response.CuisineResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuisines")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cuisine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "cuisine")
    private List<Restaurant> restaurants = new ArrayList<>();

    public CuisineResponseDTO toCuisineResponseDTO() {
        return CuisineResponseDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .build();
    }

}