package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Meeting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends CrudRepository<Meeting, Integer> {
    Meeting findMeetingById(Integer meetingId);
}
