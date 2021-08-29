package com.algaworks.algafoodapi.domain.model;

import com.algaworks.algafoodapi.api.dto.request.UpdateUserRequestDTO;
import com.algaworks.algafoodapi.api.dto.response.UserResponseDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "users_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups;

    public void updateUserData(UpdateUserRequestDTO userRequest) {
        this.setName(userRequest.getName());
        this.setEmail(userRequest.getEmail());
    }

    public UserResponseDTO toUserResponseDTO() {
        return UserResponseDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .email(this.getEmail())
                .build();
    }

}