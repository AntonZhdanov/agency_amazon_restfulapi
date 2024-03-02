package com.example.agency_amazon_restfulapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByAsinDto;
import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByDateDto;
import com.example.agency_amazon_restfulapi.exception.EntityNotFoundException;
import com.example.agency_amazon_restfulapi.security.JwtUtil;
import com.example.agency_amazon_restfulapi.service.AsinStatisticsService;
import com.example.agency_amazon_restfulapi.service.DateStatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AsinStatisticsService asinStatisticsService;

    @MockBean
    private DateStatisticsService dateStatisticsService;
    @MockBean
    private JwtUtil jwtUtil;

    @WithMockUser(roles = "ADMIN")
    @Test
    void getStatisticsByAsin_ShouldReturnStatisticsForGivenAsins() throws Exception {
        List<SalesAndTrafficByAsinDto> expectedStatistics = List.of(new SalesAndTrafficByAsinDto());
        when(asinStatisticsService.getReportsByAsin(anyList())).thenReturn(expectedStatistics);

        mockMvc.perform(get("/statistics/asin")
                        .param("asins", "ASIN1,ASIN2"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        verify(asinStatisticsService).getReportsByAsin(anyList());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void getAllStatisticsByAsin_ShouldReturnAllStatistics() throws Exception {
        List<SalesAndTrafficByAsinDto> expectedStatistics = List.of(new SalesAndTrafficByAsinDto());
        when(asinStatisticsService.getAllReportsByAllAsins(any(Pageable.class))).thenReturn(expectedStatistics);

        mockMvc.perform(get("/statistics/asin/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        verify(asinStatisticsService).getAllReportsByAllAsins(any(Pageable.class));
    }

    @WithMockUser
    @Test
    void getStatisticsByDate_ShouldReturnStatisticsForGivenDate() throws Exception {
        List<SalesAndTrafficByDateDto> expectedStatistics = List.of(new SalesAndTrafficByDateDto());
        when(dateStatisticsService.getByDate(any(LocalDate.class))).thenReturn(expectedStatistics);

        mockMvc.perform(get("/statistics/date")
                        .param("date", "2022-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        verify(dateStatisticsService).getByDate(any(LocalDate.class));
    }

    @WithMockUser
    @Test
    void getStatisticsByDateRange_ShouldReturnStatisticsForGivenDateRange() throws Exception {
        List<SalesAndTrafficByDateDto> expectedStatistics = List.of(new SalesAndTrafficByDateDto());
        when(dateStatisticsService.getByDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(expectedStatistics);

        mockMvc.perform(get("/statistics/date/range")
                        .param("startDate", "2022-01-01")
                        .param("endDate", "2022-01-02"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        verify(dateStatisticsService).getByDateBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @WithMockUser
    @Test
    void getAllStatisticsByDate_ShouldReturnAllStatistics() throws Exception {
        List<SalesAndTrafficByDateDto> expectedStatistics = List.of(new SalesAndTrafficByDateDto());
        when(dateStatisticsService.getAll(any(Pageable.class))).thenReturn(expectedStatistics);

        mockMvc.perform(get("/statistics/date/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        verify(dateStatisticsService).getAll(any(Pageable.class));
    }

    @WithMockUser
    @Test
    void getStatisticsByDate_ShouldReturnNotFoundForNonExistingDate() throws Exception {
        when(dateStatisticsService.getByDate(any(LocalDate.class)))
                .thenThrow(new EntityNotFoundException("No statistics found"));

        mockMvc.perform(get("/statistics/date")
                        .param("date", "2022-01-01"))
                .andExpect(status().isNotFound());

        verify(dateStatisticsService).getByDate(any(LocalDate.class));
    }

    @Test
    void getStatisticsByAsin_ShouldReturnUnauthorizedForUnauthenticatedUser() throws Exception {
        mockMvc.perform(get("/statistics/asin")
                        .param("asins", "ASIN1,ASIN2"))
                .andExpect(status().isUnauthorized());
    }
}
