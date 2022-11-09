package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> retrieveLocations() {
        return locationRepository.findAll();
    }
}
