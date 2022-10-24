package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Meeting extends Post {
    @NotNull
    @Column(nullable = false, length = 50)
    private String title;

    @NotNull
    @Column(nullable = false, length = 250)
    private String description;

    private Integer personQuota;

    private Integer enrolled;

    @Basic
    private Instant meetingDate;

    @JsonIgnore
    @ManyToMany(targetEntity = Client.class, mappedBy = "meetings")
    Set<Client> enrollees;
}
