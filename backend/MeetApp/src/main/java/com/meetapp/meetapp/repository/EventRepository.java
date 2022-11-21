package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    Boolean existsByIdIsAndEnrolleesContains(Integer eventId, Client enrollee);
}
