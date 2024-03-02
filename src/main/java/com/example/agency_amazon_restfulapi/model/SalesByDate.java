package com.example.agency_amazon_restfulapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesByDate {
    private MonetaryAmount orderedProductSales;
    private MonetaryAmount orderedProductSalesB2B;
    private int unitsOrdered;
    private int unitsOrderedB2B;
    private int totalOrderItems;
    private int totalOrderItemsB2B;
    private MonetaryAmount averageSalesPerOrderItem;
    private MonetaryAmount averageSalesPerOrderItemB2B;
    private double averageUnitsPerOrderItem;
    private double averageUnitsPerOrderItemB2B;
    private MonetaryAmount averageSellingPrice;
    private MonetaryAmount averageSellingPriceB2B;
    private int unitsRefunded;
    private double refundRate;
    private int claimsGranted;
    private MonetaryAmount claimsAmount;
    private MonetaryAmount shippedProductSales;
    private int unitsShipped;
    private int ordersShipped;
}
