package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.model.dto.DailySale;
import com.developer.beverageapi.domain.filter.DailySaleFilter;
import com.developer.beverageapi.domain.service.SaleQueryService;
import com.developer.beverageapi.domain.service.SaleReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/statistics")
public class ControllerStatistics {

    @Autowired
    private SaleQueryService saleQueryService;

    @Autowired
    private SaleReportService reportService;

    @GetMapping(path = "/daily-sale", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySale> consultDailySale(DailySaleFilter filter,
                                            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return saleQueryService.consultDailySale(filter, timeOffset);
    }

    @GetMapping(path = "/daily-sale", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultDailySalePDF(DailySaleFilter filter,
                                              @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

        byte[] bytesPDF = reportService.issueDailySale(filter, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sale.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPDF);
    }
}
