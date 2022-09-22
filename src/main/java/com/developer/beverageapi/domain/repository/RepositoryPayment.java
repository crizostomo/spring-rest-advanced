package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPayment extends JpaRepository<Payment, Long> {

}
