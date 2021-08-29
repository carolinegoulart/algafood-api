package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.model.State;
import com.algaworks.algafoodapi.domain.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping
    public List<State> findAll() {
        return stateService.findAll();
    }

    @GetMapping("/{stateId}")
    public State findById(@PathVariable Long stateId) {
        return stateService.findById(stateId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State create(@Valid @RequestBody State state) {
        return stateService.create(state);
    }

    @PutMapping("/{stateId}")
    public State update(@PathVariable Long stateId,
                        @Valid @RequestBody State state) {
        return stateService.update(stateId, state);
    }

    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long stateId) {
        stateService.delete(stateId);
    }
}