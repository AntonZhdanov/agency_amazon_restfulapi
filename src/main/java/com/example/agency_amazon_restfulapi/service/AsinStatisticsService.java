package com.example.agency_amazon_restfulapi.service;

import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByAsinDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AsinStatisticsService {
    List<SalesAndTrafficByAsinDto> getReportsByAsin(List<String> asins);

    List<SalesAndTrafficByAsinDto> getAllReportsByAllAsins(Pageable pageable);
}

