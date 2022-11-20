package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    public Location() {
        id = 0;
    }

    public Location(City city, Voivodeship voivodeship, Double latitude, Double longitude) {
        this();

        this.city = city;
        this.voivodeship = voivodeship;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
