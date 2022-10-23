package com.meetapp.meetapp.model;

import lombok.Data;

import java.util.Set;

@Data
public class EventDTO {
    private String token;
    private Integer locationId;
    private String title;
    private String desc;
    private Set<String> categories;
    private Integer personQuota;
    private String startDate;
    private String endDate;
    private String schedule;
}
