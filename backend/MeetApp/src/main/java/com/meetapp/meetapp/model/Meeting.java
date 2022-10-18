package com.meetapp.meetapp.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class Meeting extends Post {
    private Integer personQuota;

    private Integer enrolled;

    @Basic
    private Instant meetingDate;
}
