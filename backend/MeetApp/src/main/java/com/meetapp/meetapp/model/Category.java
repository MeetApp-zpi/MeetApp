package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "category_generator")
    @SequenceGenerator(name = "category_generator", sequenceName = "category_sequence", allocationSize = 1)
    private Integer id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "ClientInterest", joinColumns = @JoinColumn(name = "CategoryId"), inverseJoinColumns = @JoinColumn(name = "ClientId"))
    Set<Client> clients;

    public Category(String name) {
        id = 0;
        this.name = name;
    }

    public Category() {
        id = 0;
        name = "";
    }
}
