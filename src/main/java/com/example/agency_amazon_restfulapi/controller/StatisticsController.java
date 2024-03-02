package com.example.agency_amazon_restfulapi.controller;

import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByAsinDto;
import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByDateDto;
import com.example.agency_amazon_restfulapi.service.AsinStatisticsService;
import com.example.agency_amazon_restfulapi.service.DateStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
@Tag(name = "Statistics Controller", description = "Endpoints for accessing sales and traffic statistics")
public class StatisticsController {
   private final AsinStatisticsService asinStatisticsService;
   private final DateStatisticsService dateStatisticsService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/asin")
    @Operation(summary = "Retrieve statistics for specific ASINs")
    public List<SalesAndTrafficByAsinDto> getStatisticsByAsin(@RequestParam List<String> asins) {
        return asinStatisticsService.getReportsByAsin(asins);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/asin/all")
    @Operation(summary = "Retrieve statistics for all ASINs")
    public List<SalesAndTrafficByAsinDto> getAllStatisticsByAsin(Pageable pageable) {
        return asinStatisticsService.getAllReportsByAllAsins(pageable);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/date")
    @Operation(summary = "Retrieve statistics for a specific date")
    public List<SalesAndTrafficByDateDto> getStatisticsByDate(@RequestParam LocalDate date) {
        return dateStatisticsService.getByDate(date);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/date/range")
    @Operation(summary = "Retrieve statistics for a date range")
    public List<SalesAndTrafficByDateDto> getStatisticsByDateRange(@RequestParam LocalDate startDate,
                                                                   @RequestParam LocalDate endDate) {
        return dateStatisticsService.getByDateBetween(startDate, endDate);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/date/all")
    @Operation(summary = "Retrieve statistics for all dates")
    public List<SalesAndTrafficByDateDto> getAllStatisticsByDate(Pageable pageable) {
        return dateStatisticsService.getAll(pageable);
    }
}
