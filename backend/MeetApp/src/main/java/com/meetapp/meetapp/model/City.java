package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "city_generator")
    @SequenceGenerator(name = "city_generator", sequenceName = "city_sequence", allocationSize = 1)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;
}
