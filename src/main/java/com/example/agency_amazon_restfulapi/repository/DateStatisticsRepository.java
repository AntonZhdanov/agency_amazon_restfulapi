package com.example.agency_amazon_restfulapi.repository;

import com.example.agency_amazon_restfulapi.model.SalesAndTrafficByDate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DateStatisticsRepository extends MongoRepository<SalesAndTrafficByDate, String> {
    List<SalesAndTrafficByDate> findAllByDate(LocalDate date);

    List<SalesAndTrafficByDate> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}
