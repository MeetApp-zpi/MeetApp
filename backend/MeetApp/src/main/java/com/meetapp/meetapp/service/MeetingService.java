package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.model.Meeting;
import com.meetapp.meetapp.model.MeetingDTO;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.repository.MeetingRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final LocationRepository locationRepository;

    public MeetingService(MeetingRepository meetingRepository, LocationRepository locationRepository) {
        this.meetingRepository = meetingRepository;
        this.locationRepository = locationRepository;
    }
    public Set<Meeting> retrieveMeetings() {
        return (Set<Meeting>) meetingRepository.findAll();
    }
    public Meeting retrieveMeeting(Integer meetingId) {
        return meetingRepository.findMeetingById(meetingId);
    }
    public Meeting createMeeting(MeetingDTO newMeetingDTO) {
        // TODO: CONVERT TOKEN TO AUTHOR
        Location foundLoc = locationRepository.findLocationById(newMeetingDTO.getLocationId());
        Meeting newMeeting = new Meeting();
        newMeeting.setAuthor();
        newMeeting.setTitle(newMeetingDTO.getTitle());
        newMeeting.setMeetingDate(newMeetingDTO.getMeetingDate());
        newMeeting.setPersonQuota(newMeeting.getPersonQuota());
        newMeeting.setDescription(newMeeting.getDescription());
        newMeeting.setLocation(foundLoc);
        return meetingRepository.save(newMeeting);
    }
    public Meeting updateMeeting(Integer meetingId, MeetingDTO updatedMeeting) {
        // TODO: CONVERT TOKEN TO AUTHOR
        Location foundLoc = locationRepository.findLocationById(updatedMeeting.getLocationId());
        Meeting existingMeeting = meetingRepository.findMeetingById(meetingId);
        existingMeeting.setAuthor(updatedMeeting.getAuthor());
        existingMeeting.setTitle(updatedMeeting.getTitle());
        existingMeeting.setMeetingDate(updatedMeeting.getMeetingDate());
        existingMeeting.setPersonQuota(updatedMeeting.getPersonQuota());
        // I have no clue, if someone updates the whole meeting it probably means it's supposed to be activated?
        existingMeeting.setIsActive(true);
        existingMeeting.setDescription(updatedMeeting.getDesc());
        existingMeeting.setLocation(foundLoc);

        // also enrolled + enrollees shouldn't be affected by updating the meeting right?

        // I read that save() is not required to commit record changes?
        return existingMeeting;
    }
    public void deleteMeeting(Integer meetingId) {
        meetingRepository.deleteById(meetingId);
    }
}
