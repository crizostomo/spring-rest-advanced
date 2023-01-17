package com.developer.beverageapi.api.v1.swaggerapi.model;

import com.developer.beverageapi.api.v1.model.UserModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("UsersModel")
@Setter
@Getter
public class UsersModelOpenApi {

    private UsersEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("UsersEmbeddedModel")
    @Data
    public class UsersEmbeddedModelOpenApi {

        private List<UserModel> users;
    }
}
