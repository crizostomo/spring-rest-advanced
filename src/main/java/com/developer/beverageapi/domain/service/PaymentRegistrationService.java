package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.PaymentNotFoundException;
import com.developer.beverageapi.domain.model.Payment;
import com.developer.beverageapi.domain.repository.RepositoryPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentRegistrationService {

    public static final String MSG_STATE_BEING_USED = "Payment with the code %d cannot be found" +
            "because it is being used";

    @Autowired
    private RepositoryPayment repositoryPayment;

    @Transactional
    public Payment add(Payment payment){
        return repositoryPayment.save(payment);
    }

    @Transactional
    public void remove(Long paymentId){
        try{
            repositoryPayment.deleteById(paymentId);
            repositoryPayment.flush();

        } catch (EmptyResultDataAccessException e){
            throw new PaymentNotFoundException(paymentId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_STATE_BEING_USED, paymentId));
        }
    }

    public Payment searchOrFail(Long paymentId) {
        return repositoryPayment.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }
}
