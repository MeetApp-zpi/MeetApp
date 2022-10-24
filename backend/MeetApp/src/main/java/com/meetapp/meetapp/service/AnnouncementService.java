package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.dto.AnnouncementDTO;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.repository.AnnouncementRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final LocationRepository locationRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository, LocationRepository locationRepository) {
        this.announcementRepository = announcementRepository;
        this.locationRepository = locationRepository;
    }

    public Set<Announcement> retrieveAnnouncements() {
        return (Set<Announcement>) announcementRepository.findAll();
    }

    public Announcement retrieveAnnouncement(Integer announcementId) {
        var retrievedAnnouncement = announcementRepository.findById(announcementId);

        return retrievedAnnouncement.orElseThrow(() ->
                new NoSuchElementException("An announcement with id: " + announcementId + " does not exist.")
        );
    }

    public Announcement createAnnouncement(AnnouncementDTO newAnnouncementDTO) {
        // TODO: Convert token
        Location foundLoc = locationRepository.findLocationById(newAnnouncementDTO.getLocationId());
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setAuthor(new Client()); // FIXME
        newAnnouncement.setLocation(foundLoc);
        newAnnouncement.setDescription(newAnnouncementDTO.getDescription());
        newAnnouncement.setTitle(newAnnouncementDTO.getTitle());

        return newAnnouncement;
    }

    public Announcement updateAnnouncement(Integer announcementId, AnnouncementDTO updatedAnnouncement) {
        Location foundLoc = locationRepository.findLocationById(updatedAnnouncement.getLocationId());
        Announcement existingAnnouncement = announcementRepository.findAnnouncementById(announcementId);
        // Should we update creation date? probably not
        existingAnnouncement.setIsActive(true);
        existingAnnouncement.setLocation(foundLoc);
        existingAnnouncement.setTitle(updatedAnnouncement.getTitle());
        existingAnnouncement.setDescription(updatedAnnouncement.getDescription());
        return existingAnnouncement;
    }

    public void deleteAnnouncement(Integer announcementId) {
        announcementRepository.deleteById(announcementId);
    }
}
