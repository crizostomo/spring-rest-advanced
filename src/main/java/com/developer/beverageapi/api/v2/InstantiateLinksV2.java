package com.developer.beverageapi.api.v2;

import com.developer.beverageapi.api.v2.controller.ControllerCityV2;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class InstantiateLinksV2 {

    public Link linkToCities(String rel) {
        return WebMvcLinkBuilder.linkTo(ControllerCityV2.class).withRel(rel);
    }

    public Link linkToCities() {
        return linkToCities(IanaLinkRelations.SELF.value());
    }

}
