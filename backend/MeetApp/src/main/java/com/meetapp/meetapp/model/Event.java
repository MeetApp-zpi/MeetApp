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
public class Event extends Post {
    @Column(length = 3000)
    private String description;

    private Integer personQuota;

    private Integer enrolled;

    @Basic
    private Instant startDate;

    @Basic
    private Instant endDate;

    @Column(length = 500) //TODO: discuss the max length
    private String schedule;

    @JsonIgnore
    @ManyToMany(targetEntity = Client.class, mappedBy = "events")
    Set<Client> enrollees;
}
