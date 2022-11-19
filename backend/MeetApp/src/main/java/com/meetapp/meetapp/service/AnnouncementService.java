package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.AnnouncementCreationDTO;
import com.meetapp.meetapp.dto.AnnouncementDTO;
import com.meetapp.meetapp.dto.PostDTO;
import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.model.Category;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.repository.AnnouncementRepository;
import com.meetapp.meetapp.repository.CategoryRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.security.SessionManager;
import com.meetapp.meetapp.specification.AnnouncementSpecifications;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository, LocationRepository locationRepository,
                               ClientRepository clientRepository, CategoryRepository categoryRepository) {
        this.announcementRepository = announcementRepository;
        this.locationRepository = locationRepository;
        this.clientRepository = clientRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<AnnouncementDTO> retrieveAnnouncements(List<Integer> categoryIds, List<Integer> locationIds,
                                                       Integer sortOption, String nameSearch) {

        Specification<Announcement> specification = Specification.where(null);

        if (categoryIds != null) {
            specification = specification.and(AnnouncementSpecifications.hasCategory(categoryIds));
        }

        if (locationIds != null) {
            specification = specification.and(AnnouncementSpecifications.hasLocation(locationIds));
        }

        if (nameSearch != null) {
            specification = specification.and(AnnouncementSpecifications.titleContains(nameSearch));
        }

        if (sortOption != null) {
            return announcementRepository.findAll(specification, paramToSortOrThrow(sortOption)).stream()
                    .map((Announcement announcement) -> new AnnouncementDTO(new PostDTO(announcement),
                            announcement.getTitle(), announcement.getDescription(),
                            announcement.getEnrolled())).toList();
        } else {
            return announcementRepository.findAll(specification).stream()
                    .map((Announcement announcement) -> new AnnouncementDTO(new PostDTO(announcement),
                            announcement.getTitle(), announcement.getDescription(),
                            announcement.getEnrolled())).toList();
        }
    }

    public AnnouncementDTO retrieveAnnouncement(Integer announcementId) {
        val foundAnnouncement = findAnnouncementOrThrow(announcementId);
        return new AnnouncementDTO(new PostDTO(foundAnnouncement), foundAnnouncement.getTitle(),
                foundAnnouncement.getDescription(), foundAnnouncement.getEnrolled());
    }

    public Announcement createAnnouncement(AnnouncementCreationDTO newAnnouncement, HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client foundClient = findClientOrThrow(email);
        Location foundLocation = findLocationOrThrow(newAnnouncement.getLocationId());
        List<Category> foundCategories = findCategories(newAnnouncement.getCategoryIds());

        Announcement announcementToSave =
                new Announcement(foundClient, foundLocation, newAnnouncement.getDescription(),
                        newAnnouncement.getTitle(), new HashSet<>(foundCategories));

        return announcementRepository.save(announcementToSave);
    }

    public Announcement updateAnnouncement(Integer announcementId, AnnouncementCreationDTO updatedAnnouncement,
                                           HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client supposedAuthor = findClientOrThrow(email);
        Location foundLocation = findLocationOrThrow(updatedAnnouncement.getLocationId());
        Announcement foundAnnouncement = findAnnouncementOrThrow(announcementId);

        if (foundAnnouncement.getAuthor().equals(supposedAuthor)) {
            foundAnnouncement.setLocation(foundLocation);
            foundAnnouncement.setTitle(updatedAnnouncement.getTitle());
            foundAnnouncement.setDescription(updatedAnnouncement.getDescription());
            foundAnnouncement.setCategories(new HashSet<>(findCategories(updatedAnnouncement.getCategoryIds())));
            return announcementRepository.save(foundAnnouncement);
        } else {
            throw new SecurityException(
                    "Announcement with id: " + announcementId + " does not belong to the user with id: " +
                            supposedAuthor.getId());
        }
    }

    public void deleteAnnouncement(Integer announcementId, HttpSession session) {
        Announcement announcementToDelete = findAnnouncementOrThrow(announcementId);
        String email = SessionManager.retrieveEmailOrThrow(session);
        Client supposedAuthor = findClientOrThrow(email);

        if (announcementToDelete.getAuthor().equals(supposedAuthor)) {
            announcementRepository.deleteById(announcementId);
        }
    }

    public List<Category> findCategories(Set<Integer> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    public Announcement findAnnouncementOrThrow(Integer announcementId) {
        return announcementRepository.findById(announcementId).orElseThrow(
                () -> new NoSuchElementException("An announcement with id: " + announcementId + " does not exist."));
    }

    public Location findLocationOrThrow(Integer locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NoSuchElementException("A location with id: " + locationId + " does not exist."));
    }

    public Client findClientOrThrow(String email) {
        return clientRepository.findClientByEmail(email).orElseThrow(
                () -> new NoSuchElementException("A client with email: " + email + " does not exist."));
    }

    public Sort paramToSortOrThrow(Integer sortOption) {
        return switch (sortOption) {
            case 1 -> Sort.by(Sort.Direction.ASC, "enrolled");
            case 2 -> Sort.by(Sort.Direction.DESC, "enrolled");
            default -> throw new NoSuchElementException("A sortOption with id: " + sortOption + " does not exist.");
        };
    }
}
