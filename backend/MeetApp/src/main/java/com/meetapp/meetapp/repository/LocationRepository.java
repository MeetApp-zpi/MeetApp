package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {
    Location findLocationById(Integer locationId);
}
