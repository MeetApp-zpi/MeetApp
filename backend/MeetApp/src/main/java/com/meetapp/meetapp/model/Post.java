package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "post_generator")
    @SequenceGenerator(name = "post_generator", sequenceName = "post_sequence", allocationSize = 1)
    private Integer id;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private Client author;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private Location location;

    @NotNull
    @Basic
    @Column(nullable = false)
    private LocalDate creationDate;

    @NotNull
    @Column(nullable = false)
    private Boolean isActive;

    public Post(Client author, Location location) {
        this();

        this.author = author;
        this.location = location;
    }

    public Post() {
        id = 0;
        creationDate = LocalDate.now();
        isActive = true;
    }
}
