package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.api.dto.request.RestaurantRequestDTO;
import com.algaworks.algafoodapi.domain.exception.*;
import com.algaworks.algafoodapi.domain.model.Address;
import com.algaworks.algafoodapi.domain.model.City;
import com.algaworks.algafoodapi.domain.model.Cuisine;
import com.algaworks.algafoodapi.domain.model.Restaurant;
import com.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private static final String CUISINE_NOT_FOUND_MESSAGE = "Cuisine with ID %d not found";
    private static final String CITY_NOT_FOUND_MESSAGE = "City with ID %d not found";
    private static final String RESTAURANT_IN_USE_MESSAGE = "Restaurant with ID %d cannot be deleted because it is being used";

    private final RestaurantRepository restaurantRepository;
    private final CuisineService cuisineService;
    private final SmartValidator validator;
    private final CityService cityService;

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

//        public Restaurant updateFields(Long restaurantId, Map<String, String> fields, HttpServletRequest request) {
//        Restaurant restaurantFromDB = findById(restaurantId);
//
//        merge(restaurantFromDB, fields, request);
//        validate(restaurantFromDB, "restaurant");
//
//        return update(restaurantId, restaurantFromDB);
//    }

    private void validate(Restaurant restaurantFromDB, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurantFromDB, objectName);
        validator.validate(restaurantFromDB, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }

    private void merge(Restaurant restaurantFromDB, Map<String, String> fields, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurant restaurant = objectMapper.convertValue(fields, Restaurant.class);

            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    Object newValue = ReflectionUtils.getField(field, restaurant);
                    ReflectionUtils.setField(field, restaurantFromDB, newValue);
                }
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            // throw this exception so that it is captured by an existing handler (handleHttpMessageNotReadable)
            // used the argument serverHttpRequest to avoid using a deprecated constructor
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

}