package com.meetapp.meetapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class Announcement extends Post {
    @Column(length = 500)
    private String description;

    public Announcement() {
        setIsActive(true);
        setCreationDate(Instant.now());
    }
}
