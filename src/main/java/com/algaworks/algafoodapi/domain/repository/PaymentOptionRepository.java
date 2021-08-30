package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.PaymentOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOptionRepository extends JpaRepository<PaymentOption, Long> {

}