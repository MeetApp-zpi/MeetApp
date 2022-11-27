package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "city_generator")
    @SequenceGenerator(name = "city_generator", sequenceName = "city_sequence", allocationSize = 1)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 56)
    @Column(nullable = false, unique = true, length = 56)
    private String name;

    public City() {
        id = 0;
    }

    public City(String name) {
        this();

        this.name = name;
    }
}
