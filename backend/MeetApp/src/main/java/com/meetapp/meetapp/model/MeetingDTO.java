package com.meetapp.meetapp.model;

import lombok.Data;

import java.util.Set;

// TODO: Add validation according to ERD diagram
@Data
public class MeetingDTO {
    private String token;
    private Integer locationId;
    private String title;
    private String desc;
    private Set<Integer> categories;
    private Integer personQuota;
    private String meetingDate;
}
