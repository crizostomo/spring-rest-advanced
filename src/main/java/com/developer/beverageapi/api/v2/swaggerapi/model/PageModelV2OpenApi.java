package com.developer.beverageapi.api.v2.swaggerapi.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Getter
@Setter
public class PageModelV2OpenApi {

    @ApiModelProperty(example = "10", value = "Records quantity by page")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total Records")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total pages")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Page number (starts with 0")
    private Long number;
}
