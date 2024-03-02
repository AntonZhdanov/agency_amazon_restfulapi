package com.example.agency_amazon_restfulapi.dto.sales;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonetaryAmountDto {
    private double amount;
    private String currencyCode;
}
