package com.example.agency_amazon_restfulapi.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.agency_amazon_restfulapi.dto.report.StatisticsReportInfoDto;
import com.example.agency_amazon_restfulapi.dto.report.StatisticsReportSpec;
import com.example.agency_amazon_restfulapi.exception.FileReaderException;
import com.example.agency_amazon_restfulapi.service.impl.FileReaderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FileReaderServiceImplTest {
    @InjectMocks
    private FileReaderServiceImpl fileReaderService;

    @Mock
    private ObjectMapper objectMapper;

    private final String filePath = "src/test/resources/test_report.json";
    private final String invalidFilePath = "src/test/resources/nonexistent_file.json";

    @Test
    void readFile_shouldReturnStatisticsReportInfoDto() throws Exception {
        StatisticsReportInfoDto expectedDto = new StatisticsReportInfoDto();
        StatisticsReportSpec reportSpec = new StatisticsReportSpec();
        reportSpec.setReportType("GET_SALES_AND_TRAFFIC_REPORT");
        expectedDto.setReportSpec(reportSpec);

        when(objectMapper.readValue(anyString(), eq(StatisticsReportInfoDto.class)))
                .thenReturn(expectedDto);

        StatisticsReportInfoDto result = fileReaderService.readFile(filePath);

        assertThat(result).isEqualTo(expectedDto);
        verify(objectMapper, times(1)).readValue(anyString(),
                eq(StatisticsReportInfoDto.class));
    }

    @Test
    void readFile_shouldThrowFileReaderExceptionForInvalidPath() {
        assertThrows(FileReaderException.class, ()
                -> fileReaderService.readFile(invalidFilePath));
    }

    @Test
    void readFile_shouldThrowFileReaderExceptionForNonexistentFile() {
        String nonexistentFilePath = "src/test/resources/nonexistent.json";
        assertThrows(FileReaderException.class, () -> fileReaderService.readFile(nonexistentFilePath));
    }
}
