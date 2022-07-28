package com.developer.beverageapi.jpa;

import com.developer.beverageapi.BeverageApiApplication;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class MainRestaurantConsult {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(BeverageApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RepositoryRestaurant repositoryRestaurant = applicationContext.getBean(RepositoryRestaurant.class);

        List<Restaurant> restaurants = repositoryRestaurant.findAll();

        for (Restaurant restaurant : restaurants) {
            System.out.printf("%s - %f - %s\n",
                    restaurant.getName(),
                    restaurant.getDelivery(),
                    restaurant.getKitchen().getName());
        }
    }
}
