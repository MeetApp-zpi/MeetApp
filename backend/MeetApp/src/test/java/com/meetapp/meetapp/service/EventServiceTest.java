package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.EventCreationDTO;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Event;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.repository.CategoryRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.EventRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventServiceTest {
    @Test
    public void findClient() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));

        eventService.findClientOrThrow("jdorka@gmail.com");
    }

    @Test
    public void findClientThrow() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        when(clientRepository.findClientByEmail(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NoSuchElementException.class, () -> eventService.findClientOrThrow("essabyku"));
    }

    @Test
    public void findEvent() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        when(eventRepository.findById(any())).thenReturn(Optional.of(new Event()));
        eventService.findEventOrThrow(1);
    }

    @Test
    public void findEventThrow() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        when(eventRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(NoSuchElementException.class, () ->
                eventService.findEventOrThrow(1));
    }

    @Test
    public void findLocationThrow() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        when(locationRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(NoSuchElementException.class, () ->
                eventService.findLocationOrThrow(1));
    }

    @Test
    public void findLocation() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
        eventService.findLocationOrThrow(1);
    }

    @Test
    public void findCategories() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        when(categoryRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
        eventService.findCategories(new HashSet<>());
    }

    @Test
    public void parseDate() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        eventService.parseDateOrThrow("2022-10-12T20:30:30.00Z");
    }

    @Test
    public void parseDateThrow() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                eventService.parseDateOrThrow("2022-10-12T"));
    }

    @Test
    public void timeInFutureThrow() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                eventService.timeInFutureOrThrow(Instant.now().minusMillis(3)));
    }

    @Test
    public void personQuotaPositiveOrThrow() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                eventService.personQuotaPositiveOrThrow(-1));
    }

    @Test
    public void savePictureAndGetPath() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        MultipartFile multipartFile = mock(MultipartFile.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        try (MockedStatic<Files> files = Mockito.mockStatic(Files.class)) {
            files.when(() -> Files.write(any(), (byte[]) any())).thenReturn(Path.of("yo"));
            when(multipartFile.getOriginalFilename()).thenReturn("pietrucha.png");
            eventService.savePictureAndGetPath(multipartFile);
        }
    }

    @Test
    public void savePictureAndGetPathThrow() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        MultipartFile multipartFile = mock(MultipartFile.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        try (MockedStatic<Files> files = Mockito.mockStatic(Files.class)) {
            files.when(() -> Files.write(any(), (byte[]) any())).thenThrow(IOException.class);
            when(multipartFile.getOriginalFilename()).thenReturn("pietrucha.png");
            Assertions.assertThrows(RuntimeException.class, () ->
                    eventService.savePictureAndGetPath(multipartFile));
        }
    }

    @Test
    public void retrieveEvents() {
        List<Integer> categoryIds = Arrays.asList(1, 2);
        List<Integer> locationIds = Arrays.asList(3, 4);
        Integer sortOption = 1;
        String nameSearch = "test";
        Integer page = 0;

        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Page<Event> events = mock(Page.class);
        when(eventRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(events);
        eventService.retrieveEvents(categoryIds, locationIds, sortOption, nameSearch, true, 1);
        eventService.retrieveEvents(categoryIds, locationIds, 2, nameSearch, true, 1);
        eventService.retrieveEvents(categoryIds, locationIds, 3, nameSearch, true, 1);
        eventService.retrieveEvents(categoryIds, locationIds, 4, nameSearch, true, 1);
        eventService.retrieveEvents(categoryIds, locationIds, 5, nameSearch, true, 1);
        eventService.retrieveEvents(categoryIds, locationIds, null, nameSearch, true, 1);
    }

    @Test
    public void retrieveEvent() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Event testEvent = new Event(new Client(), new Location(), "ogieamrg iom", "geior nmeiormg eoirmgioe",
                Instant.now(), Instant.now(), new HashSet<>(), null, "Super imba o 17");


        when(eventRepository.findById(any())).thenReturn(Optional.of(testEvent));
        eventService.retrieveEvent(1);
    }

    @Test
    public void retrieveEditDetails() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Event testEvent = new Event(new Client(), new Location(), "ogieamrg iom", "geior nmeiormg eoirmgioe",
                Instant.now(), Instant.now(), new HashSet<>(), null, "Super imba o 17");


        when(eventRepository.findById(any())).thenReturn(Optional.of(testEvent));
        eventService.retrieveEditDetails(1);
    }

    @Test
    public void isLoggedUserEnrolled() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(eventRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);

            eventService.isLoggedUserEnrolled(1, session);
        }
    }

    @Test
    public void enrollEventFail() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Event testEvent = new Event(new Client(), new Location(), "ogieamrg iom", "geior nmeiormg eoirmgioe",
                Instant.now(), Instant.now(), new HashSet<>(), null, "Super imba o 17");


        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(eventRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(eventRepository.findById(any())).thenReturn(Optional.of(testEvent));
            when(eventRepository.save(any())).thenReturn(testEvent);
            when(clientRepository.save(any())).thenReturn(new Client());
            eventService.enrollEvent(1, session);
        }
    }

    @Test
    public void enrollEvent() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Event testEvent = new Event(new Client(), new Location(), "ogieamrg iom", "geior nmeiormg eoirmgioe",
                Instant.now(), Instant.now(), new HashSet<>(), null, "Super imba o 17");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(eventRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(false);
            when(eventRepository.findById(any())).thenReturn(Optional.of(testEvent));
            when(eventRepository.save(any())).thenReturn(testEvent);
            when(clientRepository.save(any())).thenReturn(new Client());
            eventService.enrollEvent(1, session);
        }
    }

    @Test
    public void unenrollEvent() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Event testEvent = new Event(new Client(), new Location(), "ogieamrg iom", "geior nmeiormg eoirmgioe",
                Instant.now(), Instant.now(), new HashSet<>(), null, "Super imba o 17");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(eventRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(eventRepository.findById(any())).thenReturn(Optional.of(testEvent));
            when(eventRepository.save(any())).thenReturn(testEvent);
            when(clientRepository.save(any())).thenReturn(new Client());
            eventService.unenrollEvent(1, session);
        }
    }

    @Test
    public void createEvent() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        MultipartFile file = mock(MultipartFile.class);
        HttpSession session = mock(HttpSession.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Event testEvent = new Event(new Client(), new Location(), "ogieamrg iom", "geior nmeiormg eoirmgioe",
                Instant.now(), Instant.now(), new HashSet<>(), null, "Super imba o 17");

        EventCreationDTO newEvent = new EventCreationDTO();
        newEvent.setPicture(null);
        newEvent.setStartDate("2022-12-20T15:15:00.00Z");
        newEvent.setEndDate("2022-12-20T16:12:00.00Z");
        newEvent.setPersonQuota(10);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(eventRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
            when(categoryRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
            when(file.getOriginalFilename()).thenReturn("pietrucha.jpg");
            when(eventRepository.save(any())).thenReturn(testEvent);
            eventService.createEvent(newEvent, session);
        }
    }

    @Test
    public void createEventThrow() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        MultipartFile file = mock(MultipartFile.class);
        HttpSession session = mock(HttpSession.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Event testEvent = new Event(new Client(), new Location(), "ogieamrg iom", "geior nmeiormg eoirmgioe",
                Instant.now(), Instant.now(), new HashSet<>(), null, "Super imba o 17");

        EventCreationDTO newEvent = new EventCreationDTO();
        newEvent.setPicture(null);
        newEvent.setEndDate("2022-12-20T15:15:00.00Z");
        newEvent.setStartDate("2022-12-20T16:12:00.00Z");
        newEvent.setPersonQuota(10);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(eventRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
            when(categoryRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
            when(file.getOriginalFilename()).thenReturn("pietrucha.jpg");
            when(eventRepository.save(any())).thenReturn(testEvent);

            Assertions.assertThrows(IllegalArgumentException.class, () ->
                    eventService.createEvent(newEvent, session));
        }
    }

    @Test
    public void updateEventThrowsSecurity() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        MultipartFile file = mock(MultipartFile.class);
        HttpSession session = mock(HttpSession.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Event testEvent = new Event(new Client(), new Location(), "ogieamrg iom", "geior nmeiormg eoirmgioe",
                Instant.now(), Instant.now(), new HashSet<>(), null, "Super imba o 17");

        EventCreationDTO newEvent = new EventCreationDTO();
        newEvent.setPicture(null);
        newEvent.setStartDate("2022-12-20T15:15:00.00Z");
        newEvent.setEndDate("2022-12-20T16:12:00.00Z");
        newEvent.setPersonQuota(10);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(eventRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(eventRepository.findById(any())).thenReturn(Optional.of(testEvent));
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
            when(categoryRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
            when(file.getOriginalFilename()).thenReturn("pietrucha.jpg");
            when(eventRepository.save(any())).thenReturn(testEvent);

            Assertions.assertThrows(SecurityException.class, () ->
                    eventService.updateEvent(1, newEvent, session));
        }
    }

    @Test
    public void updateEventThrowsIllegal() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        MultipartFile file = mock(MultipartFile.class);
        HttpSession session = mock(HttpSession.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Event testEvent = new Event(new Client(), new Location(), "ogieamrg iom", "geior nmeiormg eoirmgioe",
                Instant.now(), Instant.now(), new HashSet<>(), null, "Super imba o 17");

        EventCreationDTO newEvent = new EventCreationDTO();
        newEvent.setPicture(null);
        newEvent.setEndDate("2022-12-20T15:15:00.00Z");
        newEvent.setStartDate("2022-12-20T16:12:00.00Z");
        newEvent.setPersonQuota(10);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(eventRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(eventRepository.findById(any())).thenReturn(Optional.of(testEvent));
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
            when(categoryRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
            when(file.getOriginalFilename()).thenReturn("pietrucha.jpg");
            when(eventRepository.save(any())).thenReturn(testEvent);

            Assertions.assertThrows(IllegalArgumentException.class, () ->
                    eventService.updateEvent(1, newEvent, session));
        }
    }

    @Test
    public void updateEvent() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        MultipartFile file = mock(MultipartFile.class);
        HttpSession session = mock(HttpSession.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Client testClient = new Client();

        Event testEvent = new Event(testClient, new Location(), "ogieamrg iom", "geior nmeiormg eoirmgioe",
                Instant.now(), Instant.now(), new HashSet<>(), null, "Super imba o 17");

        EventCreationDTO newEvent = new EventCreationDTO();
        newEvent.setPicture(null);
        newEvent.setStartDate("2022-12-20T15:15:00.00Z");
        newEvent.setEndDate("2022-12-20T16:12:00.00Z");
        newEvent.setPersonQuota(10);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(eventRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(eventRepository.findById(any())).thenReturn(Optional.of(testEvent));
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
            when(categoryRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
            when(file.getOriginalFilename()).thenReturn("pietrucha.jpg");
            when(eventRepository.save(any())).thenReturn(testEvent);

            eventService.updateEvent(1, newEvent, session);
        }
    }

    @Test
    public void deleteEvent() {
        EventRepository eventRepository = mock(EventRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        EventService eventService = new EventService(eventRepository, locationRepository,
                clientRepository, categoryRepository);

        Client testClient = new Client();

        Event testEvent = new Event(testClient, new Location(), "ogieamrg iom", "geior nmeiormg eoirmgioe",
                Instant.now(), Instant.now(), new HashSet<>(), null, "Super imba o 17");

        EventCreationDTO newEvent = new EventCreationDTO();
        newEvent.setPicture(null);
        newEvent.setStartDate("2022-12-20T15:15:00.00Z");
        newEvent.setEndDate("2022-12-20T16:12:00.00Z");
        newEvent.setPersonQuota(10);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(testClient));
            when(eventRepository.findById(any())).thenReturn(Optional.of(testEvent));

            eventService.deleteEvent(1, session);
        }
    }
}
