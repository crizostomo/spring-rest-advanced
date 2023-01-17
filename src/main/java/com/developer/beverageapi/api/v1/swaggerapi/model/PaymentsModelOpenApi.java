package com.developer.beverageapi.api.v1.swaggerapi.model;

import com.developer.beverageapi.api.v1.model.PaymentModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PaymentsModel")
@Data
public class PaymentsModelOpenApi {

    private PaymentsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("PaymentsEmbeddedModel")
    @Data
    public class PaymentsEmbeddedModelOpenApi {

        private List<PaymentModel> payments;
    }
}
