package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.domain.service.FlowOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/orders/{codeOrder}", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerOrderStatus {

    @Autowired
    private FlowOrderService statusOrder;

    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirm(@PathVariable String codeOrder) {
        statusOrder.confirm(codeOrder);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancel(@PathVariable String codeOrder) {
        statusOrder.cancel(codeOrder);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delivery(@PathVariable String codeOrder) {
        statusOrder.delivery(codeOrder);

        return ResponseEntity.noContent().build();
    }
}
