package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.api.dto.request.RestaurantRequestDTO;
import com.algaworks.algafoodapi.domain.exception.*;
import com.algaworks.algafoodapi.domain.model.*;
import com.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private static final String CUISINE_NOT_FOUND_MESSAGE = "Cuisine with ID %d not found";
    private static final String CITY_NOT_FOUND_MESSAGE = "City with ID %d not found";
    private static final String RESTAURANT_IN_USE_MESSAGE = "Restaurant with ID %d cannot be deleted because it is being used";

    private final RestaurantRepository restaurantRepository;
    private final CuisineService cuisineService;
    private final CityService cityService;
    private final PaymentOptionService paymentService;

    @Transactional
    public Restaurant create(RestaurantRequestDTO restaurantRequest) {
        try  {
            Cuisine cuisine = cuisineService.findById(restaurantRequest.getCuisineId());
            City city = cityService.findById(restaurantRequest.getAddress().getCityId());
            Address address = restaurantRequest.getAddress().toAddress(city);

            Restaurant restaurant = restaurantRequest.toRestaurant(cuisine, address);
            restaurant.setCuisine(cuisine);
            return restaurantRepository.save(restaurant);
        } catch (CuisineNotFoundException e) {
            throw new BusinessException(String.format(CUISINE_NOT_FOUND_MESSAGE, restaurantRequest.getCuisineId()), e);
        } catch (CityNotFoundException e) {
            throw new BusinessException(String.format(CITY_NOT_FOUND_MESSAGE, restaurantRequest.getAddress().getCityId()), e);
        }
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    public void delete(Long id) {
        try {
            restaurantRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(RESTAURANT_IN_USE_MESSAGE, id));
        }
    }

    @Transactional
    public Restaurant update(Long restaurantId, RestaurantRequestDTO restaurantRequest) {
        try {
            Restaurant restaurantFromDB = findById(restaurantId);
            Cuisine cuisine = cuisineService.findById(restaurantRequest.getCuisineId());

            City city = cityService.findById(restaurantRequest.getAddress().getCityId());
            Address address = restaurantRequest.getAddress().toAddress(city);

            restaurantFromDB.updateRestaurantData(restaurantRequest, cuisine, address);
            return restaurantRepository.save(restaurantFromDB);
        } catch (CuisineNotFoundException e) {
            throw new BusinessException(String.format(CUISINE_NOT_FOUND_MESSAGE, restaurantRequest.getCuisineId()), e);
        }
    }

    public Set<PaymentOption> findAllRestaurantPaymentOptions(Long restaurantId) {
        Restaurant restaurant = findById(restaurantId);
        return restaurant.getPaymentOptions();
    }

    public void activateRestaurantPaymentOption(Long restaurantId, Long paymentId) {
        PaymentOption payment = paymentService.findById(paymentId);

        Restaurant restaurantFromDB = findById(restaurantId);
        restaurantFromDB.addPaymentOption(payment);

        restaurantRepository.save(restaurantFromDB);
    }

    public void disableRestaurantPaymentOption(Long restaurantId, Long paymentId) {
        PaymentOption payment = paymentService.findById(paymentId);

        Restaurant restaurantFromDB = findById(restaurantId);
        restaurantFromDB.removePaymentOption(payment);

        restaurantRepository.save(restaurantFromDB);
    }

}