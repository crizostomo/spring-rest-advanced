package com.developer.beverageapi.jpa;

import com.developer.beverageapi.BeverageApiApplication;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class MainKitchenExclusion {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(BeverageApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RepositoryKitchen repositoryKitchen = applicationContext.getBean(RepositoryKitchen.class);

        Kitchen kitchen1 = new Kitchen();
        kitchen1.setId(1L);

        repositoryKitchen.deleteById(kitchen1.getId());

        System.out.printf("%d - %s\n", kitchen1.getId(), kitchen1.getName());
    }
}
