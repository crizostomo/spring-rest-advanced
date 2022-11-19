package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface RepositoryPayment extends JpaRepository<Payment, Long> {

    @Query("select max (updateDate) from Payment")
    OffsetDateTime getLastUpdateDate();

}
