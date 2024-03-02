package com.example.agency_amazon_restfulapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByAsinDto;
import com.example.agency_amazon_restfulapi.exception.EntityNotFoundException;
import com.example.agency_amazon_restfulapi.mapper.AsinStatisticsMapper;
import com.example.agency_amazon_restfulapi.model.SalesAndTrafficByAsin;
import com.example.agency_amazon_restfulapi.model.SalesByAsin;
import com.example.agency_amazon_restfulapi.model.TrafficByAsin;
import com.example.agency_amazon_restfulapi.repository.AsinStatisticsRepository;
import com.example.agency_amazon_restfulapi.service.impl.AsinStatisticsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AsinStatisticsServiceImplTest {
    @Mock
    private AsinStatisticsRepository asinStatisticsRepository;

    @Mock
    private AsinStatisticsMapper asinStatisticsMapper;

    @InjectMocks
    private AsinStatisticsServiceImpl asinStatisticsService;


    @Test
    void getReportsByAsin_shouldReturnDtoListForExistingAsins() {
        List<String> asins = List.of("ASIN1", "ASIN2");
        List<SalesAndTrafficByAsin> salesAndTrafficByAsins = asins.stream()
                .map(asin -> new SalesAndTrafficByAsin("id_" + asin, asin,
                        new SalesByAsin(), new TrafficByAsin()))
                .toList();
        when(asinStatisticsRepository.findByParentAsinIn(asins)).thenReturn(salesAndTrafficByAsins);

        salesAndTrafficByAsins.forEach(salesAndTrafficByAsin -> {
            when(asinStatisticsMapper.toDto(salesAndTrafficByAsin)).thenReturn(new SalesAndTrafficByAsinDto());
        });

        List<SalesAndTrafficByAsinDto> result = asinStatisticsService.getReportsByAsin(asins);

        assertThat(result).hasSize(asins.size());
        verify(asinStatisticsRepository, times(1)).findByParentAsinIn(asins);
        salesAndTrafficByAsins.forEach(salesAndTrafficByAsin -> {
            verify(asinStatisticsMapper, times(1)).toDto(salesAndTrafficByAsin);
        });
    }

    @Test
    void getReportsByAsin_shouldThrowEntityNotFoundExceptionForNonExistingAsins() {
        List<String> asins = List.of("ASIN1", "ASIN2");
        when(asinStatisticsRepository.findByParentAsinIn(asins)).thenReturn(List.of());

        assertThrows(EntityNotFoundException.class, () -> asinStatisticsService.getReportsByAsin(asins));
        verify(asinStatisticsRepository, times(1)).findByParentAsinIn(asins);
    }

    @Test
    void getAllReportsByAllAsins_shouldReturnDtoListForAllAsins() {
        Pageable pageable = PageRequest.of(0, 10);
        List<SalesAndTrafficByAsin> salesAndTrafficByAsins = List.of(
                new SalesAndTrafficByAsin("id1", "ASIN1", new SalesByAsin(), new TrafficByAsin()),
                new SalesAndTrafficByAsin("id2", "ASIN2", new SalesByAsin(), new TrafficByAsin())
        );
        when(asinStatisticsRepository.findAll(pageable)).thenReturn(new PageImpl<>(salesAndTrafficByAsins));

        salesAndTrafficByAsins.forEach(salesAndTrafficByAsin -> {
            when(asinStatisticsMapper.toDto(salesAndTrafficByAsin)).thenReturn(new SalesAndTrafficByAsinDto());
        });

        List<SalesAndTrafficByAsinDto> result = asinStatisticsService.getAllReportsByAllAsins(pageable);

        assertThat(result).hasSize(salesAndTrafficByAsins.size());
        verify(asinStatisticsRepository, times(1)).findAll(pageable);
        salesAndTrafficByAsins.forEach(salesAndTrafficByAsin -> {
            verify(asinStatisticsMapper, times(1)).toDto(salesAndTrafficByAsin);
        });
    }

    @Test
    void getAllReportsByAllAsins_shouldReturnEmptyListForEmptyPage() {
        Pageable pageable = PageRequest.of(0, 10);
        when(asinStatisticsRepository.findAll(pageable)).thenReturn(Page.empty());

        List<SalesAndTrafficByAsinDto> result = asinStatisticsService.getAllReportsByAllAsins(pageable);

        assertThat(result).isEmpty();
        verify(asinStatisticsRepository, times(1)).findAll(pageable);
    }

    @Test
    void getAllReportsByAllAsins_shouldReturnEmptyListForNonExistingPage() {
        Pageable pageable = PageRequest.of(100, 10);
        when(asinStatisticsRepository.findAll(pageable)).thenReturn(Page.empty());

        List<SalesAndTrafficByAsinDto> result = asinStatisticsService.getAllReportsByAllAsins(pageable);

        assertThat(result).isEmpty();
        verify(asinStatisticsRepository, times(1)).findAll(pageable);
    }

    @Test
    void getAllReportsByAllAsins_shouldThrowNullPointerExceptionForNullPageable() {
        Exception exception = assertThrows(NullPointerException.class, ()
                -> asinStatisticsService.getAllReportsByAllAsins(null));
        assertEquals("Pageable must not be null", exception.getMessage());
    }
}
