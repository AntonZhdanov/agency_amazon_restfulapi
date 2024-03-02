package com.example.agency_amazon_restfulapi.service.impl;

import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByDateDto;
import com.example.agency_amazon_restfulapi.exception.EntityNotFoundException;
import com.example.agency_amazon_restfulapi.mapper.DateStatisticsMapper;
import com.example.agency_amazon_restfulapi.model.SalesAndTrafficByDate;
import com.example.agency_amazon_restfulapi.repository.DateStatisticsRepository;
import com.example.agency_amazon_restfulapi.service.DateStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DateStatisticsServiceImpl implements DateStatisticsService {
    private final DateStatisticsRepository dateRepository;
    private final DateStatisticsMapper dateMapper;


    @Override
    @Cacheable(value = "statisticsCache", key = "#date")
    public List<SalesAndTrafficByDateDto> getByDate(LocalDate date) {
        List<SalesAndTrafficByDate> result = dateRepository.findAllByDate(date);
        if (result.isEmpty()) {
            throw new EntityNotFoundException("No statistics found for date: " + date);
        }
        return result.stream()
                .map(dateMapper::toDto).toList();
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "{#dateFrom, #dateTo}")
    public List<SalesAndTrafficByDateDto> getByDateBetween(LocalDate dateFrom, LocalDate dateTo) {
        return dateRepository.findAllByDateBetween(dateFrom, dateTo).stream()
                .map(dateMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = "statisticsCache", key = "'AllDates:' + #pageable")
    public List<SalesAndTrafficByDateDto> getAll(Pageable pageable) {
        return dateRepository.findAll(pageable).stream()
                .map(dateMapper::toDto)
                .toList();
    }
}
