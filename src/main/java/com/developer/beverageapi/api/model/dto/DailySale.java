package com.developer.beverageapi.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@Setter
@Getter
public class DailySale {

    private LocalDate date;
    private Long totalSale;
    private BigDecimal totalInvoice;
}
