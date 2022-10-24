package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Event extends Post {
    @Column(nullable = false, unique = true, length = 100)
    private String title;

    @Column(nullable = false, length = 10_000)
    private String description;

    private Integer personQuota;

    private Integer enrolled;

    @Basic
    @Column(nullable = false)
    private Instant startDate;

    @Basic
    @Column(nullable = false)
    private Instant endDate;

    @Column(nullable = true, length = 5_000)
    private String schedule;

    @JsonIgnore
    @ManyToMany(targetEntity = Client.class, mappedBy = "events")
    Set<Client> enrollees;
}
