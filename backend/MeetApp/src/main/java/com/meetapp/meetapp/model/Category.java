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

    @Column(nullable = false, unique = true)
    private String name;

    public Category(String name) {
        id = 0;
        this.name = name;
    }

    public Category() {
        id = 0;
        name = "";
    }
}
