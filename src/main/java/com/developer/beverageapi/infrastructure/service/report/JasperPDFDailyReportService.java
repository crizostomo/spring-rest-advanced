package com.developer.beverageapi.infrastructure.service.report;

import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.filter.DailySaleFilter;
import com.developer.beverageapi.domain.service.SaleQueryService;
import com.developer.beverageapi.domain.service.SaleReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class JasperPDFDailyReportService implements SaleReportService {

    @Autowired
    private SaleQueryService saleQueryService;

    @Override
    public byte[] issueDailySale(DailySaleFilter filter, String timeOffset) {

        try {
            var inputStream = this.getClass().getResourceAsStream("/reports/daily-sale.jasper");

            var parameters = new HashMap<String, Object>();
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var dailySale = saleQueryService.consultDailySale(filter, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(dailySale);

            if (inputStream != null) {
                var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
                return JasperExportManager.exportReportToPdf(jasperPrint);
            } else {
                throw new BusinessException("The file could not be localized, verify if Jasper is correctly installed");
            }

        } catch (JRException e) {
            throw new ReportException("It was not possible to issue the daily report", e);
        }
    }
}
