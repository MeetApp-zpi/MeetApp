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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "city_generator")
    @SequenceGenerator(name = "city_generator", sequenceName = "city_sequence", allocationSize = 1)
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
        this.id = 0;
        this.author = author;
        this.location = location;
        this.creationDate = LocalDate.now();
        this.isActive = true;
    }

    public Post() {
        id = 0;
    }
}
