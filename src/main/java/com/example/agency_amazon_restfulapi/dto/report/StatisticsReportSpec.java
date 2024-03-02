package com.example.agency_amazon_restfulapi.dto.report;

import com.example.agency_amazon_restfulapi.dto.report.StatisticsReportOptions;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class StatisticsReportSpec {
    private String reportType;
    private StatisticsReportOptions reportOptions;
    private LocalDate dataStartTime;
    private LocalDate dataEndTime;
    private List<String> marketplaceIds;
}
