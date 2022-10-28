package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Event extends Post {
    @NotNull
    @Size(min = 5, max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String title;

    @NotNull
    @Size(min = 10, max = 10_000)
    @Column(nullable = false, length = 10_000)
    private String description;

    private Integer personQuota;

    private Integer enrolled;

    @NotNull
    @Future
    @Basic
    @Column(nullable = false)
    private Instant startDate;

    @NotNull
    @Future
    @Basic
    @Column(nullable = false)
    private Instant endDate;

    @Size(min = 1, max = 5_000) // null elements are considered valid
    @Column(nullable = true, length = 5_000)
    private String schedule;

    @JsonIgnore
    @ManyToMany(targetEntity = Client.class, mappedBy = "events")
    Set<Client> enrollees;
}
