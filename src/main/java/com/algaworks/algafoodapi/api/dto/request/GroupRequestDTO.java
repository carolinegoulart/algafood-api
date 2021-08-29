package com.algaworks.algafoodapi.api.dto.request;

import com.algaworks.algafoodapi.domain.model.Group;
import com.algaworks.algafoodapi.domain.model.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GroupRequestDTO {

    @NotBlank
    private final String name;

    @NotNull
    private final List<Long> permissionsId;

    public Group toGroup(List<Permission> permissions) {
        return Group.builder()
            .name(this.getName())
            .permissions(permissions)
            .build();
    }

}