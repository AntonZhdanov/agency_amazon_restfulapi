package com.example.agency_amazon_restfulapi.dto.sales;

import com.example.agency_amazon_restfulapi.dto.traffic.TrafficByDateDto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class SalesAndTrafficByDateDto {
    private LocalDate date;
    private SalesByDateDto salesByDate;
    private TrafficByDateDto trafficByDate;
}
