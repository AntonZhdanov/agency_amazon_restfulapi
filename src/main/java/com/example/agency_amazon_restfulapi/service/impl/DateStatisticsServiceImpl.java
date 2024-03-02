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
        if (date == null) {
            throw new IllegalArgumentException("Date must not be null");
        }

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
        if (dateFrom == null || dateTo == null) {
            throw new IllegalArgumentException("Date range must not be null");
        }
        if (dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
        if (dateFrom.isEqual(dateTo)) {
            throw new IllegalArgumentException("Start date and end date must not be equal");
        }
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
