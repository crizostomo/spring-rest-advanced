package com.developer.beverageapi.api.swaggerapi.model;

import com.developer.beverageapi.api.model.ProductModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("ProductsModel")
@Setter
@Getter
public class ProductsModelOpenApi {

    private ProductsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("ProductsEmbeddedModel")
    @Data
    public class ProductsEmbeddedModelOpenApi {

        private List<ProductModel> products;
    }
}
