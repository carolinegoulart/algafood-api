package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.dto.request.GroupRequestDTO;
import com.algaworks.algafoodapi.domain.model.Group;
import com.algaworks.algafoodapi.domain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<Group> findAll() {
        return groupService.findAll();
    }

    @GetMapping("/{groupId}")
    public Group findById(@PathVariable Long groupId) {
        return groupService.findById(groupId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Group create(@Valid @RequestBody GroupRequestDTO groupRequestDTO) {
        return groupService.create(groupRequestDTO);
    }

    @PutMapping("/{groupId}")
    public Group update(@PathVariable Long groupId,
                        @Valid @RequestBody GroupRequestDTO groupRequestDTO) {
        return groupService.update(groupId, groupRequestDTO);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long groupId) {
        groupService.delete(groupId);
    }

}