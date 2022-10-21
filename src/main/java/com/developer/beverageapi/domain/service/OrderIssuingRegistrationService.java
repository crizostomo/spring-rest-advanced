package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.OrderNotFoundException;
import com.developer.beverageapi.domain.model.*;
import com.developer.beverageapi.domain.repository.RepositoryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderIssuingRegistrationService {

    @Autowired
    private RepositoryOrder repositoryOrder;

    @Autowired
    private RestaurantRegistrationService restaurantRegistrationService;

    @Autowired
    private CityRegistrationService cityRegistrationService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private ProductRegistrationService productRegistrationService;

    @Autowired
    private PaymentRegistrationService paymentRegistrationService;

    @Transactional
    public Order issue(Order order) {
        validateOrder(order);
        validateItems(order);

        order.setDelivery(order.getRestaurant().getDelivery());
        order.calculateTotal();

        return repositoryOrder.save(order);
    }

    private void validateOrder(Order order) {
        City city = cityRegistrationService.searchOrFail(order.getAddress().getCity().getId());
        User client = userRegistrationService.searchOrFail(order.getClient().getId());
        Restaurant restaurant = restaurantRegistrationService.searchOrFail(order.getRestaurant().getId());
        Payment payment = paymentRegistrationService.searchOrFail(order.getPayment().getId());

        order.getAddress().setCity(city);
        order.setClient(client);
        order.setRestaurant(restaurant);
        order.setPayment(payment);

        if (restaurant.doesNotAcceptPayment(payment)) {
            throw new BusinessException(String.format("The payment '%s' is not accepted by this restaurant",
                    payment.getDescription()));
        }
    }

    private void validateItems(Order order) {
        order.getItems().forEach(orderItem -> {
            Product product = productRegistrationService.searchOrFail(
                    order.getRestaurant().getId(), orderItem.getProduct().getId());

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setUnitPrice(product.getPrice());
        });
    }

//    public Order searchOrFail(Long orderId) {
//        return repositoryOrder.findById(orderId)
//                .orElseThrow(() -> new OrderNotFoundException(orderId));
//    }

    public Order searchOrFail(String codeOrder) {
        return repositoryOrder.findByCode(codeOrder)
                .orElseThrow(() -> new OrderNotFoundException(codeOrder));
    }
}
