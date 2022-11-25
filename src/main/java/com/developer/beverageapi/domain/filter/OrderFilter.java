package com.developer.beverageapi.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Setter
@Getter
public class OrderFilter {

    @ApiModelProperty(example = "1", value = "Client ID for searching")
    private Long clientId;

    @ApiModelProperty(example = "1", value = "Restaurant ID for searching")
    private Long restaurantId;

    @ApiModelProperty(example = "2022-11-01T20:34:04Z", value = "Initial Date/hour created for searching")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime creationDateStart;

    @ApiModelProperty(example = "2022-11-01T20:54:04Z", value = "Final Date/hour created for searching")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime creationDateEnd;
}
