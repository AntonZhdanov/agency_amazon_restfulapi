package com.example.agency_amazon_restfulapi.service.impl;

import com.example.agency_amazon_restfulapi.dto.report.StatisticsReportInfoDto;
import com.example.agency_amazon_restfulapi.exception.FileReaderException;
import com.example.agency_amazon_restfulapi.service.FileReaderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class FileReaderServiceImpl implements FileReaderService {
    private final ObjectMapper objectMapper;

    @Override
    public StatisticsReportInfoDto readFile(String path) {
        try {
            String reportContent = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
            return objectMapper.readValue(reportContent, StatisticsReportInfoDto.class);
        } catch (IOException e) {
            throw new FileReaderException("Error reading file '"
                    + path + "': The file may be corrupted or in an unsupported format.", e);
        }
    }
}
