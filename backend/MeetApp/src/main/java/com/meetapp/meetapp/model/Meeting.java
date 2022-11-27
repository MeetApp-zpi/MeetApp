package com.meetapp.meetapp.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
public class Meeting extends Post {
    @NotNull
    @Size(min = 5, max = 50)
    @Column(nullable = false, length = 50)
    private String title;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(nullable = false, length = 250)
    private String description;

    private Integer personQuota;

    @NotNull
    @Basic
    @Column(nullable = false)
    private Instant meetingDate;

    public Meeting(Client author, Location location, String title, String description, Instant meetingDate,
                   Set<Category> categories, Integer personQuota) {
        this(author, location, title, description, meetingDate, categories);

        this.personQuota = personQuota;
    }

    public Meeting(Client author, Location location, String title, String description, Instant meetingDate,
                   Set<Category> categories) {
        super(author, location, categories);

        this.title = title;
        this.description = description;
        this.meetingDate = meetingDate;
    }

    public Meeting() {
    }
}
