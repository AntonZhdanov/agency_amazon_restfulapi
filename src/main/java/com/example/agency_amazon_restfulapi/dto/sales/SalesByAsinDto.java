package com.example.agency_amazon_restfulapi.dto.sales;

import com.example.agency_amazon_restfulapi.dto.sales.MonetaryAmountDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesByAsinDto {
    private MonetaryAmountDto orderedProductSales;
    private MonetaryAmountDto orderedProductSalesB2B;
    private int unitsOrdered;
    private int unitsOrderedB2B;
    private int totalOrderItems;
    private int totalOrderItemsB2B;
}
