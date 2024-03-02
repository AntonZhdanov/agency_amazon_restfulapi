package com.example.agency_amazon_restfulapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.agency_amazon_restfulapi.model.SalesAndTrafficByAsin;
import com.example.agency_amazon_restfulapi.model.SalesByAsin;
import com.example.agency_amazon_restfulapi.model.TrafficByAsin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Collections;
import java.util.List;

@DataMongoTest
@ActiveProfiles("test")
public class AsinStatisticsRepositoryTest {
    @Autowired
    private AsinStatisticsRepository asinStatisticsRepository;

    private SalesAndTrafficByAsin asin1;
    private SalesAndTrafficByAsin asin2;

    @BeforeEach
    void setUp() {
        asin1 = new SalesAndTrafficByAsin();
        asin1.setParentAsin("asin1");
        asin1.setSalesByAsin(new SalesByAsin());
        asin1.setTrafficByAsin(new TrafficByAsin());

        asin2 = new SalesAndTrafficByAsin();
        asin2.setParentAsin("asin2");
        asin2.setSalesByAsin(new SalesByAsin());
        asin2.setTrafficByAsin(new TrafficByAsin());

        asinStatisticsRepository.saveAll(List.of(asin1, asin2));
    }

    @AfterEach
    void tearDown() {
        asinStatisticsRepository.deleteAll();
    }

    @Test
    void findByParentAsinIn_shouldReturnAsinStatistics() {
        List<SalesAndTrafficByAsin> result = asinStatisticsRepository
                .findByParentAsinIn(List.of("asin1", "asin2"));

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(asin1, asin2);
    }

    @Test
    void findByParentAsinIn_shouldReturnEmptyListWhenAsinNotFound() {
        List<SalesAndTrafficByAsin> result = asinStatisticsRepository
                .findByParentAsinIn(List.of("non-existent-asin"));

        assertThat(result).isEmpty();
    }

    @Test
    void findByParentAsinIn_shouldReturnEmptyListForEmptyAsinList() {
        List<SalesAndTrafficByAsin> result = asinStatisticsRepository
                .findByParentAsinIn(Collections.emptyList());
        assertThat(result).isEmpty();
    }

    @Test
    void findByParentAsinIn_shouldReturnOnlyExistingAsins() {
        SalesAndTrafficByAsin asin3 = new SalesAndTrafficByAsin();
        asin3.setParentAsin("asin3");
        asin3.setSalesByAsin(new SalesByAsin());
        asin3.setTrafficByAsin(new TrafficByAsin());
        asinStatisticsRepository.save(asin3);

        List<SalesAndTrafficByAsin> result = asinStatisticsRepository
                .findByParentAsinIn(List.of("asin1", "non-existent-asin"));
        assertThat(result).hasSize(1);
        assertThat(result).containsExactlyInAnyOrder(asin1);
    }

    @Test
    void findByParentAsinIn_shouldReturnUniqueRecordsForDuplicateAsins() {
        List<SalesAndTrafficByAsin> result = asinStatisticsRepository
                .findByParentAsinIn(List.of("asin1", "asin1"));
        assertThat(result).hasSize(1);
        assertThat(result).containsExactlyInAnyOrder(asin1);
    }
}
