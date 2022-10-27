package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.model.dto.DailySale;
import com.developer.beverageapi.domain.filter.DailySaleFilter;
import com.developer.beverageapi.domain.service.SaleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/statistics")
public class ControllerStatistics {

    @Autowired
    private SaleQueryService saleQueryService;

    @GetMapping("/daily-sale")
    public List<DailySale> consultDailySale(DailySaleFilter filter) {
        return saleQueryService.consultDailySale(filter);
    }
}
