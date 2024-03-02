package com.example.agency_amazon_restfulapi.service;

import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByDateDto;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface DateStatisticsService {
    List<SalesAndTrafficByDateDto> getByDate(LocalDate date);

    List<SalesAndTrafficByDateDto> getByDateBetween(LocalDate dateFrom, LocalDate dateTo);

    List<SalesAndTrafficByDateDto> getAll(Pageable pageable);

}
