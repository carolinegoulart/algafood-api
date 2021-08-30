package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.PaymentOptionNotFoundException;
import com.algaworks.algafoodapi.domain.model.PaymentOption;
import com.algaworks.algafoodapi.domain.repository.PaymentOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentOptionService {

    private final PaymentOptionRepository paymentOptionRepository;

    public PaymentOption findById(Long id) {
        return paymentOptionRepository.findById(id)
                .orElseThrow(() -> new PaymentOptionNotFoundException(id));
    }

}