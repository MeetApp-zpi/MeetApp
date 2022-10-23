package com.meetapp.meetapp.model;

import lombok.Data;

import java.util.Set;

@Data
public class AnnouncementDTO {
    private String token;
    private Integer locationId;
    private String title;
    private String desc;
    private Set<Integer> categories;
}
