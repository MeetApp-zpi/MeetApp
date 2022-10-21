package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
public class Meeting extends Post {
    @Column(length = 1000)
    private String description;

    private Integer personQuota;

    private Integer enrolled;

    @Basic
    private Instant meetingDate;

    @JsonIgnore
    @ManyToMany(targetEntity = Client.class, mappedBy = "meetings")
    Set<Client> enrollees;
}
