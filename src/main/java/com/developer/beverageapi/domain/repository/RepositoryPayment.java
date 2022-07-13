package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Payment;

import java.util.List;

public interface RepositoryPayment {

    List<Payment> listAll();
    Payment searchById(Long id);
    Payment add(Payment payment);
    void remove(Payment payment);
}
