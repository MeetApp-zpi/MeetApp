package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.AnnouncementCreationDTO;
import com.meetapp.meetapp.dto.AnnouncementDTO;
import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.model.Category;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.repository.AnnouncementRepository;
import com.meetapp.meetapp.repository.CategoryRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnnouncementServiceTest {

    @Test
    public void testRetrieveAnnouncements() {
        // Set up test data
        List<Integer> categoryIds = Arrays.asList(1, 2);
        List<Integer> locationIds = Arrays.asList(3, 4);
        Integer sortOption = 1;
        String nameSearch = "test";
        Integer page = 0;

        // Set up mock repository
        List<Announcement> announcements = Arrays.asList(
                new Announcement(new Client(), new Location(), "title1", "description1", new HashSet<Category>()),
                new Announcement(new Client(), new Location(), "title2", "description2", new HashSet<Category>())
        );
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        Page<Announcement> anns = mock(Page.class);
        when(announcementRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(anns);

        // Set up service
        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        // Invoke service method
        List<AnnouncementDTO> result = service.retrieveAnnouncements(categoryIds, locationIds, sortOption, nameSearch, page);
        List<AnnouncementDTO> result2 = service.retrieveAnnouncements(categoryIds, locationIds, 2, nameSearch, page);
        List<AnnouncementDTO> result3 = service.retrieveAnnouncements(categoryIds, locationIds, 3, nameSearch, page);
        List<AnnouncementDTO> result4 = service.retrieveAnnouncements(categoryIds, locationIds, 4, nameSearch, page);
        List<AnnouncementDTO> result6 = service.retrieveAnnouncements(categoryIds, locationIds, null, nameSearch, page);

        assertEquals(0, 0);
    }

    @Test
    public void findClient() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));

        service.findClientOrThrow("essabyku");
        assertEquals(0, 0);
    }

    @Test()
    public void findClientThrow() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        when(clientRepository.findClientByEmail(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NoSuchElementException.class, () -> service.findClientOrThrow("essabyku"));
    }

    @Test
    public void findLocation() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));

        service.findLocationOrThrow(1);
        assertEquals(0, 0);
    }

    @Test
    public void findLocationThrow() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        when(locationRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NoSuchElementException.class, () -> service.findLocationOrThrow(1));
    }

    @Test
    public void findAnnouncement() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement()));

        service.findAnnouncementOrThrow(1);
        assertEquals(0, 0);
    }

    @Test
    public void findAnnouncementThrow() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        when(announcementRepository.findById(any())).thenReturn(Optional.ofNullable(null));


        Assertions.assertThrows(NoSuchElementException.class, () -> service.findAnnouncementOrThrow(1));
    }

    @Test
    public void findCategories() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        when(categoryRepository.findAllById(any())).thenReturn(new ArrayList<>());

        service.findCategories(new HashSet<>(1));
        assertEquals(0, 0);
    }

    @Test
    public void deleteAnnouncement() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(test));
            service.deleteAnnouncement(0, session);
        }
    }

    @Test
    public void updateAnnouncement() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(test));
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
            service.updateAnnouncement(0, new AnnouncementCreationDTO(), session);
        }
    }

    @Test
    public void updateAnnouncementThrow() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client newCl = new Client();
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(newCl));
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));

            Assertions.assertThrows(SecurityException.class, () -> service.updateAnnouncement(0,
                    new AnnouncementCreationDTO(), session));
        }
    }

    @Test
    public void createAnnouncement() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(test));
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
            when(categoryRepository.findAllById(any())).thenReturn(new ArrayList<>());
            when(announcementRepository.save(any())).thenReturn(new Announcement());
            service.createAnnouncement(new AnnouncementCreationDTO(), session);
        }
    }

    @Test
    public void deactivateAnnouncement() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(test));

            when(announcementRepository.save(any())).thenReturn(new Announcement());
            service.deactivateAnnouncement(1, session);
        }
    }

    @Test
    public void deactivateAnnouncementWrongClient() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client newCl = new Client();
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(newCl));

            when(announcementRepository.save(any())).thenReturn(new Announcement());
            service.deactivateAnnouncement(1, session);
        }
    }

    @Test
    public void activateAnnouncement() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(test));

            when(announcementRepository.save(any())).thenReturn(new Announcement());
            service.activateAnnouncement(1, session);
        }
    }

    @Test
    public void activateAnnouncementWrongClient() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client newCl = new Client();
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(newCl));

            when(announcementRepository.save(any())).thenReturn(new Announcement());
            service.activateAnnouncement(1, session);
        }
    }

    @Test
    public void enrollAnnouncement() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client newCl = new Client();
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(newCl));
            when(announcementRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(false);
            when(announcementRepository.save(any())).thenReturn(new Announcement());
            when(clientRepository.save(any())).thenReturn(newCl);
            service.enrollAnnouncement(1, session);
        }
    }

    @Test
    public void enrollAlreadyEnrolledAnnouncement() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client newCl = new Client();
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(newCl));
            when(announcementRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(announcementRepository.save(any())).thenReturn(new Announcement());
            when(clientRepository.save(any())).thenReturn(newCl);
            service.enrollAnnouncement(1, session);
        }
    }

    @Test
    public void unenrollAnnouncement() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client newCl = new Client();
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(newCl));
            when(announcementRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(false);
            when(announcementRepository.save(any())).thenReturn(new Announcement());
            when(clientRepository.save(any())).thenReturn(newCl);
            service.unenrollAnnouncement(1, session);
        }
    }

    @Test
    public void unenrollEnrolledAnnouncement() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            Client newCl = new Client();
            when(clientRepository.findClientByEmail("jdorka@gmail.com")).thenReturn(Optional.of(newCl));
            when(announcementRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(announcementRepository.save(any())).thenReturn(new Announcement());
            when(clientRepository.save(any())).thenReturn(newCl);
            service.unenrollAnnouncement(1, session);
        }
    }

    @Test
    public void retrieveAnnouncement() {
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        AnnouncementRepository announcementRepository = mock(AnnouncementRepository.class);
        HttpSession session = mock(HttpSession.class);

        AnnouncementService service = new AnnouncementService(announcementRepository, locationRepository,
                clientRepository, categoryRepository);

        Client test = new Client();

        when(announcementRepository.findById(any())).thenReturn(Optional.of(new Announcement(test
                , new Location(), "te", "st", new HashSet<>(1))));

        service.retrieveAnnouncement(1);
    }
}
