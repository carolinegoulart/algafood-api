package com.algaworks.algafoodapi;

import com.algaworks.algafoodapi.domain.exception.EntityInUseException;
import com.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import com.algaworks.algafoodapi.domain.model.Cuisine;
import com.algaworks.algafoodapi.domain.service.CuisineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CreateCuisineIT {

    @Autowired
    private CuisineService cuisineService;

    @Test
    void shouldCreateCuisineSuccessfully_WhenCuisineIsCorrect() {
        Cuisine cuisine = new Cuisine();
        cuisine.setName("Chinesa");

        Cuisine newCuisine =  cuisineService.create(cuisine);

        assertThat(newCuisine).isNotNull();
        assertThat(newCuisine.getId()).isNotNull();
    }

    @Test
    void shouldThrowException_WhenCuisineDoesNotHaveAName() {
        Cuisine cuisine = new Cuisine();
        cuisine.setName(null);

        ConstraintViolationException expectedError =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    cuisineService.create(cuisine);
                });

        assertThat(expectedError).isNotNull();
    }

    @Test
    void shouldNotDeleteCuisine_WhenCuisineIsInUse() {
        EntityInUseException expectedError =
                Assertions.assertThrows(EntityInUseException.class, () -> {
                    cuisineService.delete(1L);
                });

        assertThat(expectedError).isNotNull();
    }

    @Test
    void shouldNotDeleteCuisine_WhenCuisineDoesNotExist() {
        EntityNotFoundException expectedError =
                Assertions.assertThrows(EntityNotFoundException.class, () -> {
                    cuisineService.delete(100L);
                });

        assertThat(expectedError).isNotNull();
    }

}