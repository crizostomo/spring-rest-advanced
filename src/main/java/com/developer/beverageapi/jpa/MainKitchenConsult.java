package com.developer.beverageapi.jpa;

import com.developer.beverageapi.domain.model.Kitchen;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class MainKitchenConsult {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(MainKitchenConsult.class)
                .web(WebApplicationType.NONE)
                .run(args);

        KitchenRecord kitchenRecord = applicationContext.getBean(KitchenRecord.class);

        List<Kitchen> kitchens = kitchenRecord.list();

        for (Kitchen kitchen : kitchens) {
            System.out.println(kitchen.getName());
        }
    }
}
