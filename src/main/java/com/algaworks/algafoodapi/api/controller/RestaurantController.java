package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.dto.request.RestaurantRequestDTO;
import com.algaworks.algafoodapi.api.dto.response.RestaurantResponseDTO;
import com.algaworks.algafoodapi.domain.model.Restaurant;
import com.algaworks.algafoodapi.domain.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantResponseDTO> findAll() {
        return restaurantService.findAll().stream()
                .map(Restaurant::toRestaurantResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{restaurantId}")
    public RestaurantResponseDTO findById(@PathVariable Long restaurantId) {
        return restaurantService.findById(restaurantId).toRestaurantResponseDTO();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponseDTO create(@Valid @RequestBody RestaurantRequestDTO restaurantRequest) {
        return restaurantService.create(restaurantRequest).toRestaurantResponseDTO();
    }

    @PutMapping("/{restaurantId}")
    public RestaurantResponseDTO updateAll(@PathVariable Long restaurantId,
                                           @Valid @RequestBody RestaurantRequestDTO restaurantRequest) {
        Restaurant updatedRestaurant = restaurantService.update(restaurantId, restaurantRequest);
        return updatedRestaurant.toRestaurantResponseDTO();
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId) {
        restaurantService.delete(restaurantId);
    }
}