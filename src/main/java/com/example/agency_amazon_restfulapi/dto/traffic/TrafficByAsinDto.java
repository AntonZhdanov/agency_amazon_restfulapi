package com.example.agency_amazon_restfulapi.dto.traffic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrafficByAsinDto {
    private int browserSessions;
    private int browserSessionsB2B;
    private int mobileAppSessions;
    private int mobileAppSessionsB2B;
    private int sessions;
    private int sessionsB2B;
}
