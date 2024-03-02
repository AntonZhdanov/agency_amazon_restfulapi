package com.example.agency_amazon_restfulapi.service.scheduledservice;

import com.example.agency_amazon_restfulapi.service.cachemanager.StatisticsCacheService;
import com.example.agency_amazon_restfulapi.dto.report.StatisticsReportInfoDto;
import com.example.agency_amazon_restfulapi.mapper.AsinStatisticsMapper;
import com.example.agency_amazon_restfulapi.mapper.DateStatisticsMapper;
import com.example.agency_amazon_restfulapi.repository.AsinStatisticsRepository;
import com.example.agency_amazon_restfulapi.repository.DateStatisticsRepository;
import com.example.agency_amazon_restfulapi.service.FileReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledUpdatesCacheService {
    private static final String FILE_PATH = "src/main/resources/json-data/test_report.json";
    private final FileReaderService fileReaderService;
    private final AsinStatisticsRepository asinStatisticsRepository;
    private final DateStatisticsRepository dateStatisticsRepository;
    private final AsinStatisticsMapper asinStatisticsMapper;
    private final DateStatisticsMapper dateStatisticsMapper;
    private final StatisticsCacheService statisticsCacheService;

    @Scheduled(fixedRate = 300_000)
    @CacheEvict(cacheNames = "statisticsCache",
            allEntries = true)
    public void scheduledDatabaseUpdate() {
        StatisticsReportInfoDto updatedReport = fileReaderService.readFile(FILE_PATH);
        statisticsCacheService.clearAllCaches();

        asinStatisticsRepository.saveAll(updatedReport.getSalesAndTrafficByAsin().stream()
                .map(asinStatisticsMapper::toModel)
                .toList());

        dateStatisticsRepository.saveAll(updatedReport.getSalesAndTrafficByDate().stream()
                .map(dateStatisticsMapper::toModel)
                .toList());
    }
}
