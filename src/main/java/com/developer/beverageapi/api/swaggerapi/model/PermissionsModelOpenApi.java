package com.developer.beverageapi.api.swaggerapi.model;

import com.developer.beverageapi.api.model.PermissionModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PermissionsModel")
@Setter
@Getter
public class PermissionsModelOpenApi {

    private PermissionsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("PermissionsEmbeddedModel")
    @Data
    public class PermissionsEmbeddedModelOpenApi {

        private List<PermissionModel> permissions;
    }
}
