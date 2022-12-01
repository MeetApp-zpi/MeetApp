package com.meetapp.meetapp.controller;

import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public List<Location> getLocations(@RequestParam(required = false) String nameSearch) {
        return locationService.retrieveLocations(nameSearch);
    }
}
