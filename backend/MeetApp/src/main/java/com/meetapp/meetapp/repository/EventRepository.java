package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
    Event findEventById(Integer eventId);
}
