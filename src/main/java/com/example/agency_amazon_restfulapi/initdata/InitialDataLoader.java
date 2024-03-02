package com.example.agency_amazon_restfulapi.initdata;

import com.example.agency_amazon_restfulapi.dto.report.StatisticsReportInfoDto;
import com.example.agency_amazon_restfulapi.mapper.AsinStatisticsMapper;
import com.example.agency_amazon_restfulapi.mapper.DateStatisticsMapper;
import com.example.agency_amazon_restfulapi.repository.AsinStatisticsRepository;
import com.example.agency_amazon_restfulapi.repository.DateStatisticsRepository;
import com.example.agency_amazon_restfulapi.service.FileReaderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialDataLoader {
    private static final String FILE_PATH = "src/main/resources/json-data/test_report.json";
    private final FileReaderService fileReaderService;
    private final AsinStatisticsRepository asinStatisticsRepository;
    private final DateStatisticsRepository dateStatisticsRepository;
    private final AsinStatisticsMapper asinStatisticsMapper;
    private final DateStatisticsMapper dateStatisticsMapper;

    @PostConstruct
    private void initialize() {
        StatisticsReportInfoDto report = fileReaderService.readFile(FILE_PATH);
        asinStatisticsRepository.saveAll(report.getSalesAndTrafficByAsin().stream()
                .map(asinStatisticsMapper::toModel)
                .toList());

        dateStatisticsRepository.saveAll(report.getSalesAndTrafficByDate().stream()
                .map(dateStatisticsMapper::toModel)
                .toList());
    }
}
