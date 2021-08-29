package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}