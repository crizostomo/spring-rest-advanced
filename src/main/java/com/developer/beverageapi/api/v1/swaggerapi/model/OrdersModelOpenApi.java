package com.developer.beverageapi.api.v1.swaggerapi.model;

import com.developer.beverageapi.api.v1.model.OrderSummaryModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("OrdersSummaryModel")
@Setter
@Getter
public class OrdersModelOpenApi {

    private OrdersEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("OrdersSummaryEmbeddedModel")
    @Data
    public class OrdersEmbeddedModelOpenApi {

        private List<OrderSummaryModel> orders;
    }
}
