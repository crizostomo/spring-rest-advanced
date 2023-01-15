package com.developer.beverageapi.api.swaggerapi.model;

import com.developer.beverageapi.api.model.KitchenModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("KitchensModel")
@Setter
@Getter
public class KitchensModelOpenApi {

    private KitchensModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("KitchensEmbeddedModel")
    @Data
    public class KitchensEmbeddedModelOpenApi {

        private List<KitchenModel> kitchens;
    }
}
