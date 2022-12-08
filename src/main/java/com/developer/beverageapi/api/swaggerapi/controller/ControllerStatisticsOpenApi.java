package com.developer.beverageapi.api.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.model.CityModel;
import com.developer.beverageapi.api.model.dto.DailySale;
import com.developer.beverageapi.api.model.input.CityInput;
import com.developer.beverageapi.domain.filter.DailySaleFilter;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Statistics")
public interface ControllerStatisticsOpenApi {

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
