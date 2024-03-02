package com.example.agency_amazon_restfulapi.dto.sales;

import com.example.agency_amazon_restfulapi.dto.traffic.TrafficByAsinDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesAndTrafficByAsinDto {
    private String parentAsin;
    private SalesByAsinDto salesByAsin;
    private TrafficByAsinDto trafficByAsin;
}
