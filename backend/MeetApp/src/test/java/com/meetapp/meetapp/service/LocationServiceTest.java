package com.meetapp.meetapp.service;

import com.meetapp.meetapp.repository.LocationRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocationServiceTest {

    @Test
    public void retrieveLocations() {
        LocationRepository locationRepository = mock(LocationRepository.class);

        LocationService locationService = new LocationService(locationRepository);

        when(locationRepository.findDistinctTop10ByCityNameContainingIgnoreCaseOrderById(any()))
                .thenReturn(new ArrayList<>());

        locationService.retrieveLocations("test");
    }
}
