package com.meetapp.meetapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Announcement extends Post {
    @NotNull
    @Column(nullable = false, length = 50)
    private String title;

    @NotNull
    @Column(nullable = false, length = 200)
    private String description;
}
