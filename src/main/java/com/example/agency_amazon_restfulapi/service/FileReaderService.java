package com.example.agency_amazon_restfulapi.service;

import com.example.agency_amazon_restfulapi.dto.report.StatisticsReportInfoDto;

public interface FileReaderService {
    StatisticsReportInfoDto readFile(String path);
}
