package com.developer.beverageapi.infrasctructure.repository;

import com.developer.beverageapi.domain.model.Payment;
import com.developer.beverageapi.domain.repository.RepositoryPayment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RepositoryPaymentImpl implements RepositoryPayment {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Payment> listAll(){
        return manager.createQuery("from Payment", Payment.class)
                .getResultList();
    }

    @Override
    public Payment searchById(Long id){
        return manager.find(Payment.class, id);
    }

    @Override
    @Transactional
    public Payment add(Payment payment){
        return manager.merge(payment);
    }

    @Override
    @Transactional
    public void remove(Payment payment){
        payment = searchById(payment.getId());
        manager.remove(payment);
    }
}
