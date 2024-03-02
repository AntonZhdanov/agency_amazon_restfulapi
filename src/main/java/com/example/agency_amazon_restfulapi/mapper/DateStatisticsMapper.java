package com.example.agency_amazon_restfulapi.mapper;

import com.example.agency_amazon_restfulapi.config.MapperConfiguration;
import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByDateDto;
import com.example.agency_amazon_restfulapi.model.SalesAndTrafficByDate;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface DateStatisticsMapper {
    SalesAndTrafficByDateDto toDto(SalesAndTrafficByDate entity);
    SalesAndTrafficByDate toModel(SalesAndTrafficByDateDto dto);
}
