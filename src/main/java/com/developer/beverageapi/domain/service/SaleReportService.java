package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.filter.DailySaleFilter;

public interface SaleReportService {

    byte[] issueDailySale(DailySaleFilter filter, String timeOffset);
}
