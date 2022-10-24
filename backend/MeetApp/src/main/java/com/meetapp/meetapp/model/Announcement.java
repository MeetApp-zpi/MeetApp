package com.meetapp.meetapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Announcement extends Post {
    @Column(length = 50)
    private String title;

    @Column(length = 200)
    private String description;
}
