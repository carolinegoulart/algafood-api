package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.model.PaymentOption;
import com.algaworks.algafoodapi.domain.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/restaurants/{restaurantId}/payment-options")
@RequiredArgsConstructor
public class RestaurantPaymentOptionController {

    private final RestaurantService restaurantService;

    @GetMapping
    public Set<PaymentOption> findAll(@PathVariable Long restaurantId) {
        return restaurantService.findAllRestaurantPaymentOptions(restaurantId);
    }

    @PutMapping("/{paymentOptionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void activateRestaurantPaymentOption(@PathVariable Long restaurantId,
                                                @PathVariable Long paymentOptionId) {
        restaurantService.activateRestaurantPaymentOption(restaurantId, paymentOptionId);
    }

    @DeleteMapping("/{paymentOptionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableRestaurantPaymentOption(@PathVariable Long restaurantId,
                                               @PathVariable Long paymentOptionId) {
        restaurantService.disableRestaurantPaymentOption(restaurantId, paymentOptionId);
    }

}