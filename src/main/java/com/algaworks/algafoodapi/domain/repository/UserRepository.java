package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}