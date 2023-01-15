package com.developer.beverageapi.api.swaggerapi.model;

import com.developer.beverageapi.api.model.GroupModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("GroupsModel")
@Setter
@Getter
public class GroupsModelOpenApi {

    private GroupsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("GroupsEmbeddedModel")
    @Data
    public class GroupsEmbeddedModelOpenApi {

        private List<GroupModel> groups;
    }
}
