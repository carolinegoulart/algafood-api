package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.PermissionNotFoundException;
import com.algaworks.algafoodapi.domain.model.Permission;
import com.algaworks.algafoodapi.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private PermissionRepository permissionRepository;

    public Permission findById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(id));
    }

    public List<Permission> validateAndGetPermissions(List<Long> permissionsId) {
        List<Permission> permissions = new ArrayList<>();
        permissionsId.forEach(permission -> permissions.add(findById(permission)));
        return permissions;
    }

}