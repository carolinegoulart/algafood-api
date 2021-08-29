package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.model.City;
import com.algaworks.algafoodapi.domain.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public List<City> findAll() {
        return cityService.findAll();
    }

    @GetMapping("/{cityId}")
    public City findById(@PathVariable Long cityId) {
        return cityService.findById(cityId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public City create(@Valid @RequestBody City city) {
        return cityService.create(city);
    }

    @PutMapping("/{cityId}")
    public City update(@PathVariable Long cityId,
                       @Valid @RequestBody City city) {
        return cityService.update(cityId, city);
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cityId) {
        cityService.delete(cityId);
    }

}