package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer>, JpaSpecificationExecutor<Meeting> {
    Boolean existsByIdIsAndEnrolleesContains(Integer meetingId, Client enrollee);

    List<Meeting> findAllByIsActiveIs(Boolean isActive);
}
