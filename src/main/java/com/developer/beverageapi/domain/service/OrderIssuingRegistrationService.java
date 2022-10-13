package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.OrderNotFoundException;
import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.repository.RepositoryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderIssuingRegistrationService {

    @Autowired
    private RepositoryOrder repositoryOrder;

    public Order searchOrFail(Long orderId) {
        return repositoryOrder.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
