package com.example.agency_amazon_restfulapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesByAsin {
    private MonetaryAmount orderedProductSales;
    private MonetaryAmount orderedProductSalesB2B;
    private int unitsOrdered;
    private int unitsOrderedB2B;
    private int totalOrderItems;
    private int totalOrderItemsB2B;
}
