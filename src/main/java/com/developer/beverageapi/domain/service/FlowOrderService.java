package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.repository.RepositoryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowOrderService {

    @Autowired
    private OrderIssuingRegistrationService orderIssuing;

    @Autowired
    private RepositoryOrder repositoryOrder;

    @Transactional
    public void confirm(String codeOrder) {
        Order order = orderIssuing.searchOrFail(codeOrder);
        order.confirm();

        repositoryOrder.save(order);
    }

    @Transactional
    public void delivery(String codeOrder) {
        Order order = orderIssuing.searchOrFail(codeOrder);
        order.deliver();
    }

    @Transactional
    public void cancel(String codeOrder) {
        Order order = orderIssuing.searchOrFail(codeOrder);
        order.cancel();

        repositoryOrder.save(order);
    }
}
