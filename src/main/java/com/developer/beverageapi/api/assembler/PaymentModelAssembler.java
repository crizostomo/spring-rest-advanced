package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.PaymentModel;
import com.developer.beverageapi.domain.model.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PaymentModel toModel(Payment payment) {

        return modelMapper.map(payment, PaymentModel.class);
    }

    public List<PaymentModel> toCollectionModel(Collection<Payment> payments) {
        return payments.stream()
                .map(payment -> toModel(payment))
                .collect(Collectors.toList());
    }
}
