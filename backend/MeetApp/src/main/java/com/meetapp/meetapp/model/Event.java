package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @NotNull
    private Integer enrolled;

    @NotNull
    @Basic
    @Future
    @Column(nullable = false)
    private Instant startDate;

    @NotNull
    @Future
    @Basic
    @Column(nullable = false)
    private Instant endDate;

    @Column
    private String picture;

    @Size(min = 1, max = 5_000) // null elements are considered valid
    @Column(nullable = true, length = 5_000)
    private String schedule;

    @NotNull
    @JsonIgnore
    @ManyToMany(targetEntity = Client.class, mappedBy = "events")
    Set<Client> enrollees;

    public Event(Client author, Location location, String title, String description, Instant startDate, Instant endDate,
                 Set<Category> categories, Integer personQuota, String schedule) {
        this(author, location, title, description, startDate, endDate, categories, personQuota);

        this.schedule = schedule;
    }

    public Event(Client author, Location location, String title, String description, Instant startDate, Instant endDate,
                 Set<Category> categories, Integer personQuota) {
        this(author, location, title, description, startDate, endDate, categories);

        this.personQuota = personQuota;
    }

    public Event(Client author, Location location, String title, String description, Instant startDate, Instant endDate,
                 Set<Category> categories, String schedule) {
        this(author, location, title, description, startDate, endDate, categories);

        this.schedule = schedule;
    }

    public Event(Client author, Location location, String title, String description, Instant startDate,
                 Instant endDate, Set<Category> categories) {
        super(author, location, categories);

        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;

        this.enrollees = new HashSet<>();
        this.enrolled = 0;
    }

    public Event() {
    }
}
