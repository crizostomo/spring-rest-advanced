package com.developer.beverageapi.jpa;

import com.developer.beverageapi.BeverageApiApplication;
import com.developer.beverageapi.domain.model.Kitchen;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class MainKitchenAlteration {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(BeverageApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        KitchenRecord kitchenRecord = applicationContext.getBean(KitchenRecord.class);

        Kitchen kitchen1 = new Kitchen();
        kitchen1.setId(1L);
        kitchen1.setName("Brazilian");

        kitchen1 = kitchenRecord.save(kitchen1);

        System.out.printf("%d - %s\n", kitchen1.getId(), kitchen1.getName());
    }
}
