package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Voivodeship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "voivodeship_generator")
    @SequenceGenerator(name = "voivodeship_generator", sequenceName = "voivodeship_sequence", allocationSize = 1)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 32)
    @Column(nullable = false, unique = true, length = 32)
    private String name;

    public Voivodeship() {
        id = 0;
    }

    public Voivodeship(String name) {
        this();

        this.name = name;
    }
}
