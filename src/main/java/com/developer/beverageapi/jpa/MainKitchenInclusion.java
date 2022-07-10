package com.developer.beverageapi.jpa;

import com.developer.beverageapi.BeverageApiApplication;
import com.developer.beverageapi.domain.model.Kitchen;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class MainKitchenInclusion {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(BeverageApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        KitchenRecord kitchenRecord = applicationContext.getBean(KitchenRecord.class);

        Kitchen kitchen1 = new Kitchen();
        Kitchen kitchen2 = new Kitchen();
        kitchen1.setName("Brazilian");
        kitchen2.setName("Japanese");

        kitchen1 = kitchenRecord.add(kitchen1);
        kitchen2 = kitchenRecord.add(kitchen2);

        System.out.printf("%d - %s\n", kitchen1.getId(), kitchen1.getName());
        System.out.printf("%d - %s\n", kitchen2.getId(), kitchen2.getName());
    }
}
