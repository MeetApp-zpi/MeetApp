package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "city_generator")
    @SequenceGenerator(name = "city_generator", sequenceName = "city_sequence", allocationSize = 1)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String name;
}
