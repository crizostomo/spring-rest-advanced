package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.PaymentInputDismantle;
import com.developer.beverageapi.api.assembler.PaymentModelAssembler;
import com.developer.beverageapi.api.model.PaymentModel;
import com.developer.beverageapi.api.model.input.PaymentInput;
import com.developer.beverageapi.domain.model.Payment;
import com.developer.beverageapi.domain.repository.RepositoryPayment;
import com.developer.beverageapi.domain.service.PaymentRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payments")
public class ControllerPayment {

    @Autowired
    private RepositoryPayment repositoryPayment;

    @Autowired
    private PaymentRegistrationService registrationPayment;

    @Autowired
    private PaymentModelAssembler paymentModelAssembler;

    @Autowired
    private PaymentInputDismantle paymentInputDismantle;

    @GetMapping
    public ResponseEntity<List<PaymentModel>> list() {
        List<Payment> allPayments = repositoryPayment.findAll();

        List<PaymentModel> paymentModels = paymentModelAssembler.toCollectionModel(allPayments);

        return ResponseEntity.ok()
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//                .cacheControl(CacheControl.noCache())
//                .cacheControl(CacheControl.noStore())
                .body(paymentModels);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentModel> search(@PathVariable Long paymentId) {
        Payment payment = registrationPayment.searchOrFail(paymentId);

        PaymentModel paymentModel = paymentModelAssembler.toModel(payment);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(paymentModel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentModel add(@RequestBody @Valid PaymentInput paymentInput) {
        Payment payment = paymentInputDismantle.toDomainObject(paymentInput);

        payment = registrationPayment.add(payment);

        return paymentModelAssembler.toModel(payment);
    }

    @PutMapping("/{paymentId}")
    public PaymentModel update(@PathVariable Long paymentId,
                             @RequestBody @Valid PaymentInput paymentInput) {
        Payment currentPayment = registrationPayment.searchOrFail(paymentId);

        paymentInputDismantle.copyToDomainObject(paymentInput, currentPayment);

        currentPayment = registrationPayment.add(currentPayment);

        return paymentModelAssembler.toModel(currentPayment);
    }

    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long paymentId) {
        registrationPayment.remove(paymentId);
    }
}
