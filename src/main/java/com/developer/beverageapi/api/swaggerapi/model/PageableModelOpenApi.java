package com.developer.beverageapi.api.swaggerapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("Pageable")
@Setter
@Getter
public class PageableModelOpenApi {

    @ApiModelProperty(example = "0", value = "Page number (starts with 0)")
    private int page;
    @ApiModelProperty(example = "10", value = "Quantity of elements by page")
    private int size;
    @ApiModelProperty(example = "name,asc", value = "Property name for ordering")
    private List<String> sort;
}
