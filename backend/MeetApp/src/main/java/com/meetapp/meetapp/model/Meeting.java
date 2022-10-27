package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
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

    private Integer enrolled;

    @NotNull
    @Basic
    @Column(nullable = false)
    private Instant meetingDate;

    @JsonIgnore
    @ManyToMany(targetEntity = Client.class, mappedBy = "meetings")
    Set<Client> enrollees;

    public Meeting(Client author, Location location, String title, String description, Instant meetingDate, Integer personQuota) {
        super(author, location);

        this.title = title;
        this.description = description;
        this.meetingDate = meetingDate;

        this.personQuota = personQuota;
        this.enrolled = 0;
    }

    public Meeting(Client author, Location location, String title, String description, Instant meetingDate) {
        super(author, location);

        this.title = title;
        this.description = description;
        this.meetingDate = meetingDate;
    }

    public Meeting() {
    }
}
