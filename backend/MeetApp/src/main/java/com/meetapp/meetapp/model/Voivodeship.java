package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Voivodeship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "voivodeship_generator")
    @SequenceGenerator(name = "voivodeship_generator", sequenceName = "voivodeship_sequence", allocationSize = 1)
    private Integer id;

    @Column(nullable = false, length = 32)
    private String name;
}
