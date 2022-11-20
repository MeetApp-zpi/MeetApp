package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @NotEmpty
    @Column(nullable = false)
    @ManyToMany
    @JoinTable(name = "PostCategory", joinColumns = @JoinColumn(name = "PostId"), inverseJoinColumns = @JoinColumn(name = "CategoryId"))
    private Set<Category> categories;

    public Post(Client author, Location location, Set<Category> categories) {
        this();

        this.author = author;
        this.location = location;
        this.categories = categories;
    }

    public Post() {
        id = 0;
        creationDate = LocalDate.now();
        isActive = true;
    }
}
