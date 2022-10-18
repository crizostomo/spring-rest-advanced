package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.OrderInputDismantle;
import com.developer.beverageapi.api.assembler.OrderModelAssembler;
import com.developer.beverageapi.api.assembler.OrderSummaryModelAssembler;
import com.developer.beverageapi.api.model.OrderModel;
import com.developer.beverageapi.api.model.OrderSummaryModel;
import com.developer.beverageapi.api.model.input.OrderInput;
import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.model.User;
import com.developer.beverageapi.domain.repository.RepositoryOrder;
import com.developer.beverageapi.domain.service.OrderIssuingRegistrationService;
import com.developer.beverageapi.domain.service.StatusOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders/{orderId}")
public class ControllerStatusOrder {

    @Autowired
    private StatusOrderService statusOrder;

    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable Long orderId) {
        statusOrder.confirm(orderId);
    }

    @PutMapping("/cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long orderId) {
        statusOrder.cancel(orderId);
    }

    @PutMapping("/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delivery(@PathVariable Long orderId) {
        statusOrder.delivery(orderId);
    }
}
