package com.developer.beverageapi.core.jackson;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.model.mixin.KitchenMixin;
import com.developer.beverageapi.domain.model.mixin.RestaurantMixin;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class MixinJacksonModule extends SimpleModule {

    public MixinJacksonModule() {
        setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
        setMixInAnnotation(Kitchen.class, KitchenMixin.class);
    }
}
