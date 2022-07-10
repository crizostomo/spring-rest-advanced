package com.developer.beverageapi.jpa;

import com.developer.beverageapi.BeverageApiApplication;
import com.developer.beverageapi.domain.model.Kitchen;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class MainKitchenConsult {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(BeverageApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        KitchenRecord kitchenRecord = applicationContext.getBean(KitchenRecord.class);

        List<Kitchen> kitchens = kitchenRecord.list();

        for (Kitchen kitchen : kitchens) {
            System.out.println(kitchen.getName());
        }
    }
}
