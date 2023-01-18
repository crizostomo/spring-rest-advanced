package com.developer.beverageapi.api.v2.swaggerapi.model;

import com.developer.beverageapi.api.v1.swaggerapi.model.PageModelOpenApi;
import com.developer.beverageapi.api.v2.model.KitchenModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("KitchensModel")
@Setter
@Getter
public class KitchensModelV2OpenApi {

    private KitchensEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("KitchensEmbeddedModel")
    @Data
    public class KitchensEmbeddedModelOpenApi {

        private List<KitchenModelV2> kitchens;
    }
}
