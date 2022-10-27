package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.api.model.dto.DailySale;
import com.developer.beverageapi.domain.filter.DailySaleFilter;

import java.util.List;

public interface SaleQueryService {

    List<DailySale> consultDailySale(DailySaleFilter filter);
}
