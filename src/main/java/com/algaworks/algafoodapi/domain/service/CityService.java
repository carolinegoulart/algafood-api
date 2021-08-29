package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.BusinessException;
import com.algaworks.algafoodapi.domain.exception.CityNotFoundException;
import com.algaworks.algafoodapi.domain.exception.EntityInUseException;
import com.algaworks.algafoodapi.domain.exception.StateNotFoundException;
import com.algaworks.algafoodapi.domain.model.City;
import com.algaworks.algafoodapi.domain.model.State;
import com.algaworks.algafoodapi.domain.repository.CityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private static final String STATE_NOT_FOUND_MESSAGE = "State with ID %d not found";
    private static final String CITY_IN_USE_MESSAGE = "City with ID %d cannot be deleted because it's being used";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateService stateService;

    @Transactional
    public City create(City city) {
        try {
            Long stateId = city.getState().getId();
            State state = stateService.findById(stateId);
            city.setState(state);
            return cityRepository.save(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(String.format(STATE_NOT_FOUND_MESSAGE, city.getState().getId()), e);
        }
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City findById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
    }

    public void delete(Long id) {
        try {
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(CITY_IN_USE_MESSAGE, id));
        }
    }

    @Transactional
    public City update(Long cityId, City city) {
        try {
            City cityFromDB = findById(cityId);
            State state = stateService.findById(city.getState().getId());

            BeanUtils.copyProperties(city, cityFromDB, "id");
            cityFromDB.setState(state);

            return cityRepository.save(cityFromDB);
        } catch (StateNotFoundException e) {
            // criamos BusinessException porque precisamos de uma com status bad request
            // (atualizar cidade com estado que nao existe é bad request, e não not found)
            throw new BusinessException(String.format(STATE_NOT_FOUND_MESSAGE, city.getState().getId()), e);
        }
    }

}