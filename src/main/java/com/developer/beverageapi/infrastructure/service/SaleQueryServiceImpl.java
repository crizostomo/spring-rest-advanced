package com.developer.beverageapi.infrastructure.service;

import com.developer.beverageapi.api.model.dto.DailySale;
import com.developer.beverageapi.domain.filter.DailySaleFilter;
import com.developer.beverageapi.domain.service.SaleQueryService;

import java.util.List;

public class SaleQueryServiceImpl implements SaleQueryService {

    @Override
    public List<DailySale> consultDailySale(DailySaleFilter filter) {
        return null;
    }
}
