package com.example.agency_amazon_restfulapi.repository;

import com.example.agency_amazon_restfulapi.model.SalesAndTrafficByAsin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AsinStatisticsRepository extends MongoRepository<SalesAndTrafficByAsin, String> {
    List<SalesAndTrafficByAsin> findByParentAsinIn(List<String> parentAsin);
}
