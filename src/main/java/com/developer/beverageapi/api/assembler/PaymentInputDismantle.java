package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.input.PaymentInput;
import com.developer.beverageapi.domain.model.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentInputDismantle {

    @Autowired
    private ModelMapper modelMapper;

    public Payment toDomainObject(PaymentInput paymentInput) {

        return modelMapper.map(paymentInput, Payment.class);
    }

    public void copyToDomainObject(PaymentInput paymentInput, Payment payment) {
        modelMapper.map(paymentInput, paymentInput);
    }
}
