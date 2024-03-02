package com.example.agency_amazon_restfulapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.agency_amazon_restfulapi.model.SalesAndTrafficByDate;
import com.example.agency_amazon_restfulapi.model.SalesByDate;
import com.example.agency_amazon_restfulapi.model.TrafficByDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.util.List;

@DataMongoTest
@ActiveProfiles("test")
public class DateStatisticsRepositoryTest {
    @Autowired
    private DateStatisticsRepository dateStatisticsRepository;

    private SalesAndTrafficByDate dateStats1;
    private SalesAndTrafficByDate dateStats2;

    @BeforeEach
    void setUp() {
        dateStats1 = new SalesAndTrafficByDate();
        dateStats1.setDate(LocalDate.of(2022, 1, 1));
        dateStats1.setSalesByDate(new SalesByDate());
        dateStats1.setTrafficByDate(new TrafficByDate());

        dateStats2 = new SalesAndTrafficByDate();
        dateStats2.setDate(LocalDate.of(2022, 1, 2));
        dateStats2.setSalesByDate(new SalesByDate());
        dateStats2.setTrafficByDate(new TrafficByDate());

        dateStatisticsRepository.saveAll(List.of(dateStats1, dateStats2));
    }

    @AfterEach
    void tearDown() {
        dateStatisticsRepository.deleteAll();
    }

    @Test
    void findAllByDate_shouldReturnDateStatistics() {
        List<SalesAndTrafficByDate> result = dateStatisticsRepository.findAllByDate(LocalDate
                .of(2022, 1, 1));

        assertThat(result).hasSize(1);
        assertThat(result).containsExactlyInAnyOrder(dateStats1);
    }

    @Test
    void findAllByDateBetween_shouldReturnDateStatisticsInRange() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 2).plusDays(1);

        List<SalesAndTrafficByDate> result = dateStatisticsRepository.findAllByDateBetween(startDate, endDate);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(dateStats1, dateStats2);

    }

    @Test
    void findAllByDate_shouldReturnEmptyListForNonExistingDate() {
        List<SalesAndTrafficByDate> result = dateStatisticsRepository.findAllByDate(LocalDate
                .of(2023, 1, 1));

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByDateBetween_shouldReturnEmptyListForNonExistingDateRange() {
        List<SalesAndTrafficByDate> result = dateStatisticsRepository.findAllByDateBetween(LocalDate
                .of(2023, 1, 1), LocalDate.of(2023, 1, 2));

        assertThat(result).isEmpty();
    }
    @Test
    void findAllByDateBetween_shouldReturnOneDateStatisticsForSingleDateInRange() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 1).plusDays(1);

        List<SalesAndTrafficByDate> result = dateStatisticsRepository.findAllByDateBetween(startDate, endDate);

        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(dateStats1);
    }

    @Test
    void findAllByDateBetween_shouldReturnEmptyListWhenRangeDoesNotIncludeAnyExistingRecords() {
        LocalDate startDate = LocalDate.of(2022, 1, 3);
        LocalDate endDate = LocalDate.of(2022, 1, 4);
        List<SalesAndTrafficByDate> result = dateStatisticsRepository.findAllByDateBetween(startDate, endDate);
        assertThat(result).isEmpty();
    }

    @Test
    void findAllByDateBetween_shouldReturnStatisticsForBothDates() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 2);
        List<SalesAndTrafficByDate> result = dateStatisticsRepository
                .findAllByDateBetween(startDate, endDate.plusDays(1));
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(dateStats1, dateStats2);
    }

    @Test
    void findAllByDateBetween_shouldReturnEmptyListForDateRangeOutsideExistingRecords() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 2);
        List<SalesAndTrafficByDate> result = dateStatisticsRepository
                .findAllByDateBetween(startDate, endDate);
        assertThat(result).isEmpty();
    }

    @Test
    void findAllByDateBetween_shouldReturnStatisticsForSingleDate() {
        LocalDate date = LocalDate.of(2022, 1, 1);
        List<SalesAndTrafficByDate> result = dateStatisticsRepository
                .findAllByDateBetween(date, date.plusDays(1));
        assertThat(result).hasSize(1);
        assertThat(result).containsExactlyInAnyOrder(dateStats1);
    }
}
