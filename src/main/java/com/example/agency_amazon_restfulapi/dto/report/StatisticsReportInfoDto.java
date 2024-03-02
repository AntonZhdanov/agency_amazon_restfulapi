package com.example.agency_amazon_restfulapi.dto.report;

import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByAsinDto;
import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByDateDto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class StatisticsReportInfoDto {
    private StatisticsReportSpec reportSpec;
    List<SalesAndTrafficByAsinDto> salesAndTrafficByAsin;
    List<SalesAndTrafficByDateDto> salesAndTrafficByDate;
}
