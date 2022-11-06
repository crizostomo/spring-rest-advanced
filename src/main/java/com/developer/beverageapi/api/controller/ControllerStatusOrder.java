package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.domain.service.FlowOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/{codeOrder}")
public class ControllerStatusOrder {

    @Autowired
    private FlowOrderService statusOrder;

    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable String codeOrder) {
        statusOrder.confirm(codeOrder);
    }

    @PutMapping("/cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable String codeOrder) {
        statusOrder.cancel(codeOrder);
    }

    @PutMapping("/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delivery(@PathVariable String codeOrder) {
        statusOrder.delivery(codeOrder);
    }
}
