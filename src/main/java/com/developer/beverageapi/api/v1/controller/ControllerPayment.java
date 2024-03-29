package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.assembler.PaymentInputDismantle;
import com.developer.beverageapi.api.v1.assembler.PaymentModelAssembler;
import com.developer.beverageapi.api.v1.model.PaymentModel;
import com.developer.beverageapi.api.v1.model.input.PaymentInput;
import com.developer.beverageapi.api.v1.swaggerapi.controller.ControllerPaymentOpenApi;
import com.developer.beverageapi.core.security.CheckSecurity;
import com.developer.beverageapi.core.security.Security;
import com.developer.beverageapi.domain.model.Payment;
import com.developer.beverageapi.domain.repository.RepositoryPayment;
import com.developer.beverageapi.domain.service.PaymentRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerPayment implements ControllerPaymentOpenApi {

    @Autowired
    private RepositoryPayment repositoryPayment;

    @Autowired
    private PaymentRegistrationService registrationPayment;

    @Autowired
    private PaymentModelAssembler paymentModelAssembler;

    @Autowired
    private PaymentInputDismantle paymentInputDismantle;

    @Autowired
    private Security security;

    @CheckSecurity.Payment.AllowedToConsult
    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<PaymentModel>> list(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime lastUpdateDate = repositoryPayment.getLastUpdateDate();

        if (lastUpdateDate != null) {
            eTag = String.valueOf(lastUpdateDate.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) { // Necessary in case if there is no change
            return null;
        }

        List<Payment> allPayments = repositoryPayment.findAll();

        CollectionModel<PaymentModel> paymentModels = paymentModelAssembler.toCollectionModel(allPayments);

        return ResponseEntity.ok()
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
//                .cacheControl(CacheControl.noCache())
//                .cacheControl(CacheControl.noStore())
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
//                .header("ETag", eTag) --> Another option
                .body(paymentModels);
    }

    @CheckSecurity.Payment.AllowedToConsult
    @GetMapping(value = "/{paymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentModel> search(@PathVariable Long paymentId, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime updateDate = repositoryPayment.getUpdateDateById(paymentId);

        if (updateDate != null) {
            eTag = String.valueOf(updateDate.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        Payment payment = registrationPayment.searchOrFail(paymentId);

        PaymentModel paymentModel = paymentModelAssembler.toModel(payment);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(paymentModel);
    }

    @CheckSecurity.Payment.AllowedToEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentModel add(@RequestBody @Valid PaymentInput paymentInput) {
        Payment payment = paymentInputDismantle.toDomainObject(paymentInput);

        payment = registrationPayment.add(payment);

        return paymentModelAssembler.toModel(payment);
    }

    @CheckSecurity.Payment.AllowedToEdit
    @PutMapping("/{paymentId}")
    public PaymentModel update(@PathVariable Long paymentId,
                               @RequestBody @Valid PaymentInput paymentInput) {
        Payment currentPayment = registrationPayment.searchOrFail(paymentId);

        paymentInputDismantle.copyToDomainObject(paymentInput, currentPayment);

        currentPayment = registrationPayment.add(currentPayment);

        return paymentModelAssembler.toModel(currentPayment);
    }

    @CheckSecurity.Payment.AllowedToEdit
    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long paymentId) {
        registrationPayment.remove(paymentId);
    }
}
