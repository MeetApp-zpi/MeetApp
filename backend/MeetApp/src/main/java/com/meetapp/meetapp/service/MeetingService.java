package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Meeting;
import com.meetapp.meetapp.repository.MeetingRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }
    public Set<Meeting> retrieveMeetings() {
        return (Set<Meeting>) meetingRepository.findAll();
    }
    public Meeting retrieveMeeting(Integer meetingId) {
        return meetingRepository.findMeetingById(meetingId);
    }
    public Meeting createMeeting(Meeting newMeeting) {
        return meetingRepository.save(newMeeting);
    }
    public Meeting updateMeeting(Integer meetingId, Meeting updatedMeeting) {
        Meeting existingMeeting = meetingRepository.findMeetingById(meetingId);
        existingMeeting.setAuthor(updatedMeeting.getAuthor());
        existingMeeting.setTitle(updatedMeeting.getTitle());
        existingMeeting.setMeetingDate(updatedMeeting.getMeetingDate());
        existingMeeting.setEnrolled(updatedMeeting.getEnrolled());
        existingMeeting.setEnrollees(updatedMeeting.getEnrollees());
        return updatedMeeting;
    }
    public void deleteMeeting(Integer meetingId) {
        meetingRepository.deleteById(meetingId);
    }
}
