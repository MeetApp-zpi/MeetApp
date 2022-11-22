package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.*;
import com.meetapp.meetapp.model.*;
import com.meetapp.meetapp.repository.CategoryRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.repository.MeetingRepository;
import com.meetapp.meetapp.security.SessionManager;
import com.meetapp.meetapp.specification.MeetingSpecifications;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;

    public MeetingService(MeetingRepository meetingRepository, LocationRepository locationRepository,
                          ClientRepository clientRepository, CategoryRepository categoryRepository) {
        this.meetingRepository = meetingRepository;
        this.locationRepository = locationRepository;
        this.clientRepository = clientRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<MeetingDTO> retrieveMeetings(List<Integer> categoryIds, List<Integer> locationIds,
                                             Integer sortOption, String nameSearch) {

        Specification<Meeting> specification = Specification.where(null);

        if (categoryIds != null) {
            specification = specification.and(MeetingSpecifications.hasCategory(categoryIds));
        }

        if (locationIds != null) {
            specification = specification.and(MeetingSpecifications.hasLocation(locationIds));
        }

        if (nameSearch != null) {
            specification = specification.and(MeetingSpecifications.titleContains(nameSearch));
        }

        if (sortOption != null) {
            return meetingRepository.findAll(specification, paramToSortOrThrow(sortOption)).stream()
                    .map((Meeting meeting) -> new MeetingDTO(new PostDTO(meeting), meeting.getTitle(),
                            meeting.getDescription(), meeting.getEnrolled(), meeting.getPersonQuota(),
                            new DateTimeDTO(meeting.getMeetingDate()))).toList();
        } else {
            return meetingRepository.findAll(specification).stream()
                    .map((Meeting meeting) -> new MeetingDTO(new PostDTO(meeting), meeting.getTitle(),
                            meeting.getDescription(), meeting.getEnrolled(), meeting.getPersonQuota(),
                            new DateTimeDTO(meeting.getMeetingDate()))).toList();
        }
    }

    public MeetingDTO retrieveMeeting(Integer meetingId) {
        val foundMeeting = findMeetingOrThrow(meetingId);
        return new MeetingDTO(new PostDTO(foundMeeting), foundMeeting.getTitle(), foundMeeting.getDescription(),
                foundMeeting.getEnrolled(), foundMeeting.getPersonQuota(),
                new DateTimeDTO(foundMeeting.getMeetingDate()));
    }

    public Boolean isLoggedUserEnrolled(Integer meetingId, HttpSession session) {
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
        return meetingRepository.existsByIdIsAndEnrolleesContains(meetingId, foundClient);
    }

    public MeetingDTO enrollMeeting(Integer meetingId, HttpSession session) {
        Meeting foundMeeting = findMeetingOrThrow(meetingId);
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));

        Integer personQuota = foundMeeting.getPersonQuota();
        Integer currentlyEnrolled = foundMeeting.getEnrolled();

        if (currentlyEnrolled < personQuota && !isLoggedUserEnrolled(meetingId, session)) {
            foundClient.getPosts().add(foundMeeting);
            foundMeeting.setEnrolled(foundMeeting.getEnrolled() + 1);
        }

        Meeting savedMeeting = meetingRepository.save(foundMeeting);
        Client savedClient = clientRepository.save(foundClient);
        return new MeetingDTO(new PostDTO(new Post(savedClient,
                savedMeeting.getLocation(), savedMeeting.getCategories())), savedMeeting.getTitle(),
                savedMeeting.getDescription(), savedMeeting.getEnrolled(), savedMeeting.getPersonQuota(),
                new DateTimeDTO(savedMeeting.getMeetingDate()));
    }

    public MeetingDTO unenrollMeeting(Integer meetingId, HttpSession session) {
        Meeting foundMeeting = findMeetingOrThrow(meetingId);
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));

        if (isLoggedUserEnrolled(meetingId, session)) {
            foundClient.getPosts().remove(foundMeeting);
            foundMeeting.setEnrolled(foundMeeting.getEnrolled() - 1);
        }

        Client savedClient = clientRepository.save(foundClient);
        Meeting savedMeeting = meetingRepository.save(foundMeeting);
        return new MeetingDTO(new PostDTO(new Post(savedClient,
                savedMeeting.getLocation(), savedMeeting.getCategories())), savedMeeting.getTitle(),
                savedMeeting.getDescription(), savedMeeting.getEnrolled(), savedMeeting.getPersonQuota(),
                new DateTimeDTO(savedMeeting.getMeetingDate()));
    }

    public Meeting createMeeting(MeetingCreationDTO newMeeting, HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        Instant castedDate = parseDateOrThrow(newMeeting.getMeetingDate());
        Location foundLocation = findLocationOrThrow(newMeeting.getLocationId());
        Client foundClient = findClientOrThrow(email);
        List<Category> foundCategories = findCategories(newMeeting.getCategoryIds());

        Meeting meetingToSave = new Meeting(foundClient, foundLocation, newMeeting.getDescription(),
                newMeeting.getTitle(), castedDate, new HashSet<>(foundCategories), newMeeting.getPersonQuota());

        return meetingRepository.save(meetingToSave);
    }

    public Meeting updateMeeting(Integer meetingId, MeetingCreationDTO updatedMeeting, HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client supposedAuthor = findClientOrThrow(email);
        Location foundLocation = findLocationOrThrow(updatedMeeting.getLocationId());
        Meeting foundMeeting = findMeetingOrThrow(meetingId);
        Instant parsedDate = parseDateOrThrow(updatedMeeting.getMeetingDate());

        if (foundMeeting.getAuthor().equals(supposedAuthor)) {
            foundMeeting.setTitle(updatedMeeting.getTitle());
            foundMeeting.setMeetingDate(parsedDate);
            foundMeeting.setPersonQuota(updatedMeeting.getPersonQuota());
            foundMeeting.setDescription(updatedMeeting.getDescription());
            foundMeeting.setLocation(foundLocation);
            foundMeeting.setCategories(new HashSet<>(findCategories(updatedMeeting.getCategoryIds())));
            return meetingRepository.save(foundMeeting);
        } else {
            throw new SecurityException("Meeting with id: " + meetingId + " does not belong to the user with id: " +
                    supposedAuthor.getId());
        }
    }

    public void deleteMeeting(Integer meetingId, HttpSession session) {
        Meeting meetingToDelete = findMeetingOrThrow(meetingId);
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client supposedAuthor = findClientOrThrow(email);

        if (meetingToDelete.getAuthor().equals(supposedAuthor)) {
            meetingRepository.deleteById(meetingId);
        }
    }

    public List<Category> findCategories(Set<Integer> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    public Meeting findMeetingOrThrow(Integer meetingId) {
        return meetingRepository.findById(meetingId).orElseThrow(
                () -> new NoSuchElementException("A meeting with id: " + meetingId + " does not exist."));
    }

    public Location findLocationOrThrow(Integer locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NoSuchElementException("A location with id: " + locationId + " does not exist."));
    }

    public Client findClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email).orElseThrow(
                () -> new NoSuchElementException("A client with email: " + email + " does not exist."));
    }

    public Instant parseDateOrThrow(String dateTime) {
        try {
            return Instant.parse(dateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("A string '" + dateTime + "' is not in a valid time format");
        }
    }

    public Sort paramToSortOrThrow(Integer sortOption) {
        return switch (sortOption) {
            case 2 -> Sort.by(Sort.Direction.ASC, "creationDate").and(Sort.by(Sort.Direction.ASC, "Id"));
            case 3 -> Sort.by(Sort.Direction.ASC, "enrolled");
            case 4 -> Sort.by(Sort.Direction.DESC, "enrolled");
            case 5 -> Sort.by(Sort.Direction.ASC, "meetingDate");
            default -> Sort.by(Sort.Direction.DESC, "creationDate").and(Sort.by(Sort.Direction.DESC, "Id"));
        };
    }
}
