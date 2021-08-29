package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.model.Cuisine;
import com.algaworks.algafoodapi.domain.service.CuisineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cuisines")
public class CuisineController {

    @Autowired
    private CuisineService cuisineService;

    @GetMapping
    public List<Cuisine> list() {
        return cuisineService.findAll();
    }

    @GetMapping("/{cuisineId}")
    public Cuisine findById(@PathVariable Long cuisineId) {
        return cuisineService.findById(cuisineId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuisine create(@Valid @RequestBody Cuisine cuisine) {
        return cuisineService.create(cuisine);
    }

    @PutMapping("/{cuisineId}")
    public Cuisine update(@PathVariable Long cuisineId,
                          @Valid @RequestBody Cuisine cuisine) {
        return cuisineService.update(cuisineId, cuisine);
    }

    @DeleteMapping("/{cuisineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cuisineId) {
        cuisineService.delete(cuisineId);
    }

}