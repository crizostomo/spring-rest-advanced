package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.v1.controller.ControllerStatistics.StatisticsModel;
import com.developer.beverageapi.api.v1.model.dto.DailySale;
import com.developer.beverageapi.domain.filter.DailySaleFilter;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Statistics", description = "It runs statistics")
//@Api(tags = "Statistics")
public interface ControllerStatisticsOpenApi {

    @ApiOperation(value = "Statistics", hidden = true)
    StatisticsModel statistics();

    @ApiOperation(value = "It consults daily sale statistics")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restaurantId", value = "Restaurant Id", example = "1", dataType = "int"),
            @ApiImplicitParam(name = "creationDateStart", value = "Initial creation order Date/Time",
                    example = "2022-12-01T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "creationDateEnd", value = "Ending creation order Date/Time",
                    example = "2022-12-02T23:59:59Z", dataType = "date-time")
    })
    public List<DailySale> consultDailySale(DailySaleFilter filter,
                                            @ApiParam(value = "Time change to be taken into consideration when consulting according to UTC",
                                                    defaultValue = "+00:00") String timeOffset);

    public ResponseEntity<byte[]> consultDailySalePDF(DailySaleFilter filter, String timeOffset);
}
