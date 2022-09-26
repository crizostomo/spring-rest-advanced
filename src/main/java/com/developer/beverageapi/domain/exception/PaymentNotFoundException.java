package com.developer.beverageapi.domain.exception;

public class PaymentNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public PaymentNotFoundException(String message){
        super (message);
    }

    public PaymentNotFoundException(Long paymentId){
        this(String.format("There is no Payment with the code %d", paymentId));
    }
}
