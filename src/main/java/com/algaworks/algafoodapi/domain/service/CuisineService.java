package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.CuisineNotFoundException;
import com.algaworks.algafoodapi.domain.exception.EntityInUseException;
import com.algaworks.algafoodapi.domain.model.Cuisine;
import com.algaworks.algafoodapi.domain.repository.CuisineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuisineService {

    private static final String CUISINE_NOT_FOUND_MESSAGE = "Cuisine with ID %d not found";
    private static final String CUISINE_IN_USE_MESSAGE = "Cuisine with ID %d cannot be deleted because it's being used";

    private final CuisineRepository cuisineRepository;

    @Transactional
    public Cuisine create(Cuisine cuisine) {
        return cuisineRepository.save(cuisine);
    }

    public List<Cuisine> findAll() {
        return cuisineRepository.findAll();
    }

    public Cuisine findById(Long id) {
        return cuisineRepository.findById(id)
                .orElseThrow(() -> new CuisineNotFoundException(id));
    }

    public void delete(Long id) {
        try {
            // assim é melhor, pois realizamos somente uma consulta ao banco (se buscar pelo ID antes, seriam 2)
            cuisineRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CuisineNotFoundException(String.format(CUISINE_NOT_FOUND_MESSAGE, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(CUISINE_IN_USE_MESSAGE, id));
        }
    }

    public Cuisine update(Long id, Cuisine cuisine) {
        Cuisine cuisineFromDB = findById(id);
        // copia todas as propriedades, com exceção do ID
        BeanUtils.copyProperties(cuisine, cuisineFromDB, "id");
        return cuisineRepository.save(cuisineFromDB);
    }
}