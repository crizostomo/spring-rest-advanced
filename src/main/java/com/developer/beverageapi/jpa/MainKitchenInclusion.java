package com.developer.beverageapi.jpa;

import com.developer.beverageapi.BeverageApiApplication;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class MainKitchenInclusion {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(BeverageApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RepositoryKitchen repositoryKitchen = applicationContext.getBean(RepositoryKitchen.class);

        Kitchen kitchen1 = new Kitchen();
        Kitchen kitchen2 = new Kitchen();
        kitchen1.setName("Brazilian");
        kitchen2.setName("Japanese");

        kitchen1 = repositoryKitchen.save(kitchen1);
        kitchen2 = repositoryKitchen.save(kitchen2);

        System.out.printf("%d - %s\n", kitchen1.getId(), kitchen1.getName());
        System.out.printf("%d - %s\n", kitchen2.getId(), kitchen2.getName());
    }
}
