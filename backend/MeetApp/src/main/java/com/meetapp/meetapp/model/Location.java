package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "location_generator")
    @SequenceGenerator(name = "location_generator", sequenceName = "location_sequence", allocationSize = 1)
    private Integer id;

    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private City city;

    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private Voivodeship voivodeship;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "latitude", nullable = false)
    private Double latitude;
}
