package com.developer.beverageapi.core.jackson;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.mixin.KitchenMixin;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class MixinJacksonModule extends SimpleModule {

    public MixinJacksonModule() {
        setMixInAnnotation(Kitchen.class, KitchenMixin.class);
    }
}
