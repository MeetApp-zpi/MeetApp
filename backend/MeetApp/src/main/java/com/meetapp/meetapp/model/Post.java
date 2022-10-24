package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "city_generator")
    @SequenceGenerator(name = "city_generator", sequenceName = "city_sequence", allocationSize = 1)
    private Integer id;

    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private Client author;

    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private Location location;

    @Basic
    @Column(nullable = false)
    private Instant creationDate;

    @Column(nullable = false)
    private Boolean isActive;

    public Post(Client author, Location location) {
        this.author = author;
        this.location = location;
    }

    public Post() {
        id = 0;
    }
}
