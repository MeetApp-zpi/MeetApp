package com.meetapp.meetapp.service;

import com.meetapp.meetapp.dto.MeetingCreationDTO;
import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Event;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.model.Meeting;
import com.meetapp.meetapp.repository.CategoryRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.LocationRepository;
import com.meetapp.meetapp.repository.MeetingRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeetingServiceTest {
    @Test
    public void findClient() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));

        meetingService.findClientOrThrow("jdorka@gmail.com");
    }

    @Test
    public void findClientThrow() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        when(clientRepository.findClientByEmail(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(NoSuchElementException.class, () -> meetingService.findClientOrThrow("essabyku"));
    }

    @Test
    public void findLocationThrow() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        when(locationRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(NoSuchElementException.class, () ->
                meetingService.findLocationOrThrow(1));
    }

    @Test
    public void findLocation() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
        meetingService.findLocationOrThrow(1);
    }

    @Test
    public void findCategories() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        when(categoryRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
        meetingService.findCategories(new HashSet<>());
    }

    @Test
    public void parseDate() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        meetingService.parseDateOrThrow("2022-10-12T20:30:30.00Z");
    }

    @Test
    public void parseDateThrow() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                meetingService.parseDateOrThrow("2022-10-12T"));
    }

    @Test
    public void timeInFutureThrow() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                meetingService.timeInFutureOrThrow(Instant.now().minusMillis(3)));
    }

    @Test
    public void personQuotaPositiveOrThrow() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                meetingService.personQuotaPositiveOrThrow(-1));
    }

    @Test
    public void findMeeting() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        when(meetingRepository.findById(any())).thenReturn(Optional.of(new Meeting()));
        meetingService.findMeetingOrThrow(1);
    }

    @Test
    public void findEventThrow() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        when(meetingRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(NoSuchElementException.class, () ->
                meetingService.findMeetingOrThrow(1));
    }

    @Test
    public void retrieveMeetings() {
        List<Integer> categoryIds = Arrays.asList(1, 2);
        List<Integer> locationIds = Arrays.asList(3, 4);
        Integer sortOption = 1;
        String nameSearch = "test";
        Integer page = 0;

        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        Page<Event> meetings = mock(Page.class);
        when(meetingRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(meetings);
        meetingService.retrieveMeetings(categoryIds, locationIds, sortOption, nameSearch, 1);
        meetingService.retrieveMeetings(categoryIds, locationIds, 2, nameSearch, 1);
        meetingService.retrieveMeetings(categoryIds, locationIds, 3, nameSearch, 1);
        meetingService.retrieveMeetings(categoryIds, locationIds, 4, nameSearch, 1);
        meetingService.retrieveMeetings(categoryIds, locationIds, 5, nameSearch, 1);
        meetingService.retrieveMeetings(categoryIds, locationIds, null, nameSearch, 1);
    }

    @Test
    public void retrieveMeeting() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        Meeting newMeeting = new Meeting(new Client(), new Location(), "geogimaerg", "gieur ngiuenruigeuig",
                Instant.now(), new HashSet<>());

        when(meetingRepository.findById(any())).thenReturn(Optional.of(newMeeting));
        meetingService.retrieveMeeting(1);
    }

    @Test
    public void isLoggedUserEnrolled() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        Meeting newMeeting = new Meeting(new Client(), new Location(), "geogimaerg", "gieur ngiuenruigeuig",
                Instant.now(), new HashSet<>());

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(meetingRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            meetingService.isLoggedUserEnrolled(1, session);
        }
    }

    @Test
    public void enrollMeeting() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        Meeting newMeeting = new Meeting(new Client(), new Location(), "geogimaerg", "gieur ngiuenruigeuig",
                Instant.now(), new HashSet<>());

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(meetingRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(false);
            when(meetingRepository.findById(any())).thenReturn(Optional.of(newMeeting));
            when(meetingRepository.save(any())).thenReturn(newMeeting);
            when(clientRepository.save(any())).thenReturn(new Client());
            meetingService.enrollMeeting(1, session);
        }
    }

    @Test
    public void unenrollMeeting() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        Meeting newMeeting = new Meeting(new Client(), new Location(), "geogimaerg", "gieur ngiuenruigeuig",
                Instant.now(), new HashSet<>());

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(meetingRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(meetingRepository.findById(any())).thenReturn(Optional.of(newMeeting));
            when(meetingRepository.save(any())).thenReturn(newMeeting);
            when(clientRepository.save(any())).thenReturn(new Client());
            meetingService.unenrollMeeting(1, session);
        }
    }

    @Test
    public void createMeeting() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        MeetingCreationDTO newMeeting = new MeetingCreationDTO();
        newMeeting.setMeetingDate("2022-12-30T14:30:20.00Z");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(meetingRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(categoryRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
            when(meetingRepository.save(any())).thenReturn(new Meeting());
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
            when(clientRepository.save(any())).thenReturn(new Client());
            meetingService.createMeeting(newMeeting, session);
        }
    }

    @Test
    public void updateMeeting() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        MeetingCreationDTO newMeeting = new MeetingCreationDTO();
        newMeeting.setMeetingDate("2022-12-30T14:30:20.00Z");

        Client newClient = new Client();
        Meeting meeting = new Meeting(newClient, new Location(), "Rreag", "Bokeboa kb",
                Instant.now(), new HashSet<>());

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(newClient));
            when(meetingRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(categoryRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
            when(meetingRepository.save(any())).thenReturn(meeting);
            when(meetingRepository.findById(any())).thenReturn(Optional.of(meeting));
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
            when(clientRepository.save(any())).thenReturn(newClient);
            meetingService.updateMeeting(1, newMeeting, session);
        }
    }

    @Test
    public void updateMeetingThrow() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        MeetingCreationDTO newMeeting = new MeetingCreationDTO();
        newMeeting.setMeetingDate("2022-12-30T14:30:20.00Z");

        Client newClient = new Client();
        Meeting meeting = new Meeting(newClient, new Location(), "Rreag", "Bokeboa kb",
                Instant.now(), new HashSet<>());

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(meetingRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(categoryRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
            when(meetingRepository.save(any())).thenReturn(meeting);
            when(meetingRepository.findById(any())).thenReturn(Optional.of(meeting));
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
            when(clientRepository.save(any())).thenReturn(newClient);
            Assertions.assertThrows(SecurityException.class, () ->
                    meetingService.updateMeeting(1, newMeeting, session));
        }
    }

    @Test
    public void deleteMeeting() {
        MeetingRepository meetingRepository = mock(MeetingRepository.class);
        LocationRepository locationRepository = mock(LocationRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        HttpSession session = mock(HttpSession.class);

        MeetingService meetingService = new MeetingService(meetingRepository, locationRepository,
                clientRepository, categoryRepository);

        MeetingCreationDTO newMeeting = new MeetingCreationDTO();
        newMeeting.setMeetingDate("2022-12-30T14:30:20.00Z");

        Client newClient = new Client();
        Meeting meeting = new Meeting(newClient, new Location(), "Rreag", "Bokeboa kb",
                Instant.now(), new HashSet<>());

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            sm.when(() -> SessionManager.retrieveEmailOrThrow(any())).thenReturn("jdorka@gmail.com");
            when(clientRepository.findClientByEmail(any())).thenReturn(Optional.of(new Client()));
            when(meetingRepository.existsByIdIsAndEnrolleesContains(any(), any())).thenReturn(true);
            when(categoryRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());
            when(meetingRepository.save(any())).thenReturn(meeting);
            when(meetingRepository.findById(any())).thenReturn(Optional.of(meeting));
            when(locationRepository.findById(any())).thenReturn(Optional.of(new Location()));
            when(clientRepository.save(any())).thenReturn(newClient);
            meetingService.deleteMeeting(1, session);
        }
    }
}
