package com.developer.beverageapi.api.swaggerapi.model;

import com.developer.beverageapi.api.model.KitchenModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageModelOpenApi<T> {

    private List<T> content;

    @ApiModelProperty(example = "10", value = "Records quantity by page")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total Records")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total pages")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Page number (starts with 0")
    private Long number;
}
