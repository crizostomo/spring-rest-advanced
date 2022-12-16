package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.InstantiateLinks;
import com.developer.beverageapi.api.controller.ControllerPayment;
import com.developer.beverageapi.api.model.PaymentModel;
import com.developer.beverageapi.domain.model.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PaymentModelAssembler extends RepresentationModelAssemblerSupport<Payment, PaymentModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    public PaymentModelAssembler() {
        super(ControllerPayment.class, PaymentModel.class);
    }

    @Override
    public PaymentModel toModel(Payment payment) {
        PaymentModel paymentModel = createModelWithId(payment.getId(), payment);

        modelMapper.map(payment, paymentModel);

        paymentModel.add(instantiateLinks.linkToPayment("payments"));

        return paymentModel;
    }

    public CollectionModel<PaymentModel> toCollectionModel(Iterable<? extends Payment> entities) {
        return super.toCollectionModel(entities).add(instantiateLinks.linkToPayment());
    }
}
