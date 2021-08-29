package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.EntityInUseException;
import com.algaworks.algafoodapi.domain.exception.StateNotFoundException;
import com.algaworks.algafoodapi.domain.model.State;
import com.algaworks.algafoodapi.domain.repository.StateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StateService {

    private static final String STATE_IN_USE_MESSAGE = "State with ID %d cannot be deleted because it is being used";

    @Autowired
    private StateRepository stateRepository;

    @Transactional
    public State create(State state) {
        return stateRepository.save(state);
    }

    public List<State> findAll() {
        return stateRepository.findAll();
    }

    public State findById(Long id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new StateNotFoundException(id));
    }

    public void delete(Long id) {
        try {
            stateRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(STATE_IN_USE_MESSAGE, id));
        }
    }

    public State update(Long stateId, State state) {
        State stateFromDB = findById(stateId);
        BeanUtils.copyProperties(state, stateFromDB, "id");
        return stateRepository.save(stateFromDB);
    }

}