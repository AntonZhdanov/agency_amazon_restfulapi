package com.example.agency_amazon_restfulapi.service.impl;

import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByAsinDto;
import com.example.agency_amazon_restfulapi.mapper.AsinStatisticsMapper;
import com.example.agency_amazon_restfulapi.repository.AsinStatisticsRepository;
import com.example.agency_amazon_restfulapi.service.AsinStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsinStatisticsServiceImpl implements AsinStatisticsService {
    private final AsinStatisticsRepository asinRepository;
    private final AsinStatisticsMapper asinMapper;


    @Override
    @Cacheable(value = "reportByAsin", key = "#asins")
    public List<SalesAndTrafficByAsinDto> getReportsByAsin(List<String> asins) {
        return asinRepository.findByParentAsinIn(asins).stream()
                .map(asinMapper::toDto)
                .toList();

    }

    @Override
    @Cacheable(value = "allReportByAsins", key = "#pageable")
    public List<SalesAndTrafficByAsinDto> getAllReportsByAllAsins(Pageable pageable) {
        return asinRepository.findAll(pageable).stream()
                .map(asinMapper::toDto)
                .toList();
    }
}
