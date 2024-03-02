package com.example.agency_amazon_restfulapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByDateDto;
import com.example.agency_amazon_restfulapi.dto.sales.SalesByDateDto;
import com.example.agency_amazon_restfulapi.dto.traffic.TrafficByDateDto;
import com.example.agency_amazon_restfulapi.exception.EntityNotFoundException;
import com.example.agency_amazon_restfulapi.mapper.DateStatisticsMapper;
import com.example.agency_amazon_restfulapi.model.SalesAndTrafficByDate;
import com.example.agency_amazon_restfulapi.model.SalesByDate;
import com.example.agency_amazon_restfulapi.model.TrafficByDate;
import com.example.agency_amazon_restfulapi.repository.DateStatisticsRepository;
import com.example.agency_amazon_restfulapi.service.impl.DateStatisticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DateStatisticsServiceImplTest {
    @Mock
    private DateStatisticsRepository dateStatisticsRepository;

    @Mock
    private DateStatisticsMapper dateStatisticsMapper;

    @InjectMocks
    private DateStatisticsServiceImpl dateStatisticsService;

    private SalesAndTrafficByDate salesAndTrafficByDate;
    private SalesAndTrafficByDateDto salesAndTrafficByDateDto;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        date = LocalDate.of(2022, 1, 1);
        salesAndTrafficByDate = new SalesAndTrafficByDate("id", date,
                new SalesByDate(), new TrafficByDate());
        salesAndTrafficByDateDto = new SalesAndTrafficByDateDto(date,
                new SalesByDateDto(), new TrafficByDateDto());
    }

    @Test
    void getByDate_shouldReturnDtoListForExistingDate() {
        when(dateStatisticsRepository.findAllByDate(date)).thenReturn(List.of(salesAndTrafficByDate));
        when(dateStatisticsMapper.toDto(salesAndTrafficByDate)).thenReturn(salesAndTrafficByDateDto);

        List<SalesAndTrafficByDateDto> result = dateStatisticsService.getByDate(date);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(salesAndTrafficByDateDto);
        verify(dateStatisticsRepository, times(1)).findAllByDate(date);
        verify(dateStatisticsMapper, times(1)).toDto(salesAndTrafficByDate);
    }

    @Test
    void getByDate_shouldThrowEntityNotFoundExceptionForNonExistingDate() {
        when(dateStatisticsRepository.findAllByDate(date)).thenReturn(List.of());

        assertThrows(EntityNotFoundException.class, () -> dateStatisticsService.getByDate(date));
        verify(dateStatisticsRepository, times(1)).findAllByDate(date);
    }

    @Test
    void getByDateBetween_shouldReturnDtoListForDateRange() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 2);
        when(dateStatisticsRepository.findAllByDateBetween(startDate, endDate))
                .thenReturn(List.of(salesAndTrafficByDate));
        when(dateStatisticsMapper.toDto(salesAndTrafficByDate)).thenReturn(salesAndTrafficByDateDto);

        List<SalesAndTrafficByDateDto> result = dateStatisticsService.getByDateBetween(startDate, endDate);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(salesAndTrafficByDateDto);
        verify(dateStatisticsRepository, times(1))
                .findAllByDateBetween(startDate, endDate);
        verify(dateStatisticsMapper, times(1)).toDto(salesAndTrafficByDate);
    }

    @Test
    void getAll_shouldReturnDtoListForAllDates() {
        Pageable pageable = PageRequest.of(0, 10);
        when(dateStatisticsRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(salesAndTrafficByDate)));
        when(dateStatisticsMapper.toDto(salesAndTrafficByDate)).thenReturn(salesAndTrafficByDateDto);

        List<SalesAndTrafficByDateDto> result = dateStatisticsService.getAll(pageable);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(salesAndTrafficByDateDto);
        verify(dateStatisticsRepository, times(1)).findAll(pageable);
        verify(dateStatisticsMapper, times(1)).toDto(salesAndTrafficByDate);
    }

    @Test
    void getByDateBetween_shouldReturnEmptyListForNonExistingDateRange() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 2);
        when(dateStatisticsRepository.findAllByDateBetween(startDate, endDate)).thenReturn(List.of());

        List<SalesAndTrafficByDateDto> result = dateStatisticsService.getByDateBetween(startDate, endDate);

        assertThat(result).isEmpty();
        verify(dateStatisticsRepository, times(1)).findAllByDateBetween(startDate, endDate);
    }

    @Test
    void getAll_shouldReturnEmptyListWhenNoDataExists() {
        Pageable pageable = PageRequest.of(0, 10);
        when(dateStatisticsRepository.findAll(pageable)).thenReturn(Page.empty());

        List<SalesAndTrafficByDateDto> result = dateStatisticsService.getAll(pageable);

        assertThat(result).isEmpty();
        verify(dateStatisticsRepository, times(1)).findAll(pageable);
    }

    @Test
    void getByDate_shouldThrowIllegalArgumentExceptionForNullDate() {
        assertThrows(IllegalArgumentException.class, () -> dateStatisticsService.getByDate(null));
    }

    @Test
    void getByDateBetween_shouldHandleNullStartDate() {
        LocalDate endDate = LocalDate.of(2022, 1, 2);
        assertThrows(IllegalArgumentException.class, ()
                -> dateStatisticsService.getByDateBetween(null, endDate));
    }

    @Test
    void getByDateBetween_shouldHandleNullEndDate() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        assertThrows(IllegalArgumentException.class, ()
                -> dateStatisticsService.getByDateBetween(startDate, null));
    }

    @Test
    void getAll_shouldReturnListOfAllDateStatistics() {
        Pageable pageable = PageRequest.of(0, 10);
        List<SalesAndTrafficByDate> salesAndTrafficByDates = List.of(
                new SalesAndTrafficByDate("id1", LocalDate.of(2022, 1, 1),
                        new SalesByDate(), new TrafficByDate()),
                new SalesAndTrafficByDate("id2", LocalDate.of(2022, 1, 2),
                        new SalesByDate(), new TrafficByDate())
        );
        when(dateStatisticsRepository.findAll(pageable)).thenReturn(new PageImpl<>(salesAndTrafficByDates));

        salesAndTrafficByDates.forEach(salesAndTrafficByDate -> {
            when(dateStatisticsMapper.toDto(salesAndTrafficByDate)).thenReturn(new SalesAndTrafficByDateDto());
        });

        List<SalesAndTrafficByDateDto> result = dateStatisticsService.getAll(pageable);

        assertThat(result).hasSize(salesAndTrafficByDates.size());
        verify(dateStatisticsRepository, times(1)).findAll(pageable);
        salesAndTrafficByDates.forEach(salesAndTrafficByDate -> {
            verify(dateStatisticsMapper, times(1)).toDto(salesAndTrafficByDate);
        });
    }

    @Test
    void getAll_shouldReturnEmptyListWhenNoDateStatisticsExist() {
        Pageable pageable = PageRequest.of(0, 10);
        when(dateStatisticsRepository.findAll(pageable)).thenReturn(Page.empty());

        List<SalesAndTrafficByDateDto> result = dateStatisticsService.getAll(pageable);

        assertThat(result).isEmpty();
        verify(dateStatisticsRepository, times(1)).findAll(pageable);
    }

    @Test
    void getByDateBetween_shouldThrowIllegalArgumentExceptionForStartDateAfterEndDate() {
        LocalDate startDate = LocalDate.of(2022, 1, 2);
        LocalDate endDate = LocalDate.of(2022, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> dateStatisticsService
                .getByDateBetween(startDate, endDate));
    }

    @Test
    void getByDateBetween_shouldThrowIllegalArgumentExceptionForEqualStartAndEndDate() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 1);
        assertThrows(IllegalArgumentException.class, ()
                -> dateStatisticsService.getByDateBetween(startDate, endDate));
    }
}
