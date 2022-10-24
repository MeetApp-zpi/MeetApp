package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "location_generator")
    @SequenceGenerator(name = "location_generator", sequenceName = "location_sequence", allocationSize = 1)
    private Integer id;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private City city;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private Voivodeship voivodeship;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;
}
