package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Announcement extends Post {
    @NotNull
    @Size(min = 5, max = 50)
    @Column(nullable = false, length = 50)
    private String title;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(nullable = false, length = 200)
    private String description;

    @NotNull
    private Integer enrolled;

    @NotNull
    @JsonIgnore
    @ManyToMany(targetEntity = Client.class, mappedBy = "events")
    Set<Client> enrollees;

    public Announcement(Client author, Location location, String title, String description) {
        super(author, location);

        this.title = title;
        this.description = description;

        this.enrollees = new HashSet<>();
        this.enrolled = 0;
    }

    public Announcement() {
    }
}
