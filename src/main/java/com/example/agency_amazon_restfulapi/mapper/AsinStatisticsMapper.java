package com.example.agency_amazon_restfulapi.mapper;

import com.example.agency_amazon_restfulapi.config.MapperConfiguration;
import com.example.agency_amazon_restfulapi.dto.sales.SalesAndTrafficByAsinDto;
import com.example.agency_amazon_restfulapi.model.SalesAndTrafficByAsin;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface AsinStatisticsMapper {
    SalesAndTrafficByAsinDto toDto(SalesAndTrafficByAsin salesAndTrafficByAsin);

    SalesAndTrafficByAsin toModel(SalesAndTrafficByAsinDto salesAndTrafficByAsinDto);
}
