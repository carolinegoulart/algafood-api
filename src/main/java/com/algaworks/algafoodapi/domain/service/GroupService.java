package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.api.dto.request.GroupRequestDTO;
import com.algaworks.algafoodapi.domain.exception.EntityInUseException;
import com.algaworks.algafoodapi.domain.exception.GroupNotFoundException;
import com.algaworks.algafoodapi.domain.model.Group;
import com.algaworks.algafoodapi.domain.model.Permission;
import com.algaworks.algafoodapi.domain.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private static final String GROUP_IN_USE_MESSAGE = "Group with ID %d cannot be deleted because it is being used";

    private final GroupRepository groupRepository;
    private final PermissionService permissionService;

    public Group create(GroupRequestDTO groupRequestDTO) {
        List<Permission> permissions = permissionService.validateAndGetPermissions(groupRequestDTO.getPermissionsId());
        Group group = groupRequestDTO.toGroup(permissions);
        return groupRepository.save(group);
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));
    }

    public Group update(Long id, GroupRequestDTO groupRequestDTO) {
        Group groupFromDB = findById(id);
        List<Permission> permissions = permissionService.validateAndGetPermissions(groupRequestDTO.getPermissionsId());

        groupFromDB.setName(groupRequestDTO.getName());
        groupFromDB.setPermissions(permissions);

        return groupRepository.save(groupFromDB);
    }

    public void delete(Long id) {
        try {
            groupRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new GroupNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(GROUP_IN_USE_MESSAGE, id));
        }
    }

}