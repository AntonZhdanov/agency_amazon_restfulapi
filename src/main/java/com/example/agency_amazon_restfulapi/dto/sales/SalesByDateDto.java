package com.example.agency_amazon_restfulapi.dto.sales;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesByDateDto {
    private MonetaryAmountDto orderedProductSales;
    private MonetaryAmountDto orderedProductSalesB2B;
    private int unitsOrdered;
    private int unitsOrderedB2B;
    private int totalOrderItems;
    private int totalOrderItemsB2B;
    private MonetaryAmountDto averageSalesPerOrderItem;
    private MonetaryAmountDto averageSalesPerOrderItemB2B;
    private double averageUnitsPerOrderItem;
    private double averageUnitsPerOrderItemB2B;
    private MonetaryAmountDto averageSellingPrice;
    private MonetaryAmountDto averageSellingPriceB2B;
    private int unitsRefunded;
    private double refundRate;
    private int claimsGranted;
    private MonetaryAmountDto claimsAmount;
    private MonetaryAmountDto shippedProductSales;
    private int unitsShipped;
    private int ordersShipped;
}
