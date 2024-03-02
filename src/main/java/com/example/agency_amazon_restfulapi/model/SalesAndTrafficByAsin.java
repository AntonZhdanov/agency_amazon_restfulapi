package com.example.agency_amazon_restfulapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports-by-traffic-asin")
@Getter
@Setter
public class SalesAndTrafficByAsin {
    @Id
    private String id;

    private String parentAsin;

    private SalesByAsin salesByAsin;

    private TrafficByAsin trafficByAsin;
}
