package com.meetapp.meetapp.service;

import com.meetapp.meetapp.configuration.Constants;
import com.meetapp.meetapp.dto.*;
import com.meetapp.meetapp.model.*;
import com.meetapp.meetapp.repository.CategoryRepository;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.repository.PostRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    public ClientService(ClientRepository clientRepository, CategoryRepository categoryRepository,
                         PostRepository postRepository) {
        this.clientRepository = clientRepository;
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    public static Record postToDto(Post post) {
        if (post instanceof Announcement casted) {
            return new AnnouncementDTO(new PostDTO(post), casted.getTitle(), casted.getDescription(),
                    casted.getEnrolled());
        } else if (post instanceof Meeting casted) {
            return new MeetingDTO(new PostDTO(post), casted.getTitle(), casted.getDescription(),
                    casted.getEnrolled(), casted.getPersonQuota(), new DateTimeDTO(casted.getMeetingDate()));
        } else {
            Event casted = (Event) post;
            return new EventDTO(new PostDTO(post), casted.getTitle(), casted.getDescription(),
                    casted.getEnrolled(), casted.getPersonQuota(), casted.getSchedule(),
                    new DateTimeDTO(casted.getStartDate()), new DateTimeDTO(casted.getEndDate()),
                    casted.getPicture());
        }
    }

    public ClientDetailsDTO retrieveClientDetails(HttpSession session) {
        Client foundClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
        return new ClientDetailsDTO(foundClient.getId(), foundClient.getEmail(), foundClient.getFirstName(),
                foundClient.getLastName(), foundClient.getProfilePicture(), foundClient.getIsDeleted(),
                foundClient.getInterests());
    }

    public List<Record> retrieveClientPosts(Integer clientId, Integer page) {
        PageRequest nextPage = PageRequest.of(page, Constants.PAGE_SIZE);
        return postRepository.findAllByAuthorEmailIsAndIsActiveIs(findClientOrThrow(clientId).getEmail(),
                        true, nextPage)
                .stream().map(ClientService::postToDto).toList();
    }

    public List<Record> retrieveClientPosts(HttpSession session, Integer page) {
        PageRequest nextPage = PageRequest.of(page, Constants.PAGE_SIZE);
        return postRepository.findAllByAuthorEmailIsAndIsActiveIs(SessionManager.retrieveEmailOrThrow(session),
                        true, nextPage)
                .stream().map(ClientService::postToDto).toList();
    }

    public List<Record> retrieveClientInactivePosts(HttpSession session, Integer page) {
        PageRequest nextPage = PageRequest.of(page, Constants.PAGE_SIZE);
        return postRepository.findAllByAuthorEmailIsAndIsActiveIs(SessionManager.retrieveEmailOrThrow(session),
                        false, nextPage)
                .stream().map(ClientService::postToDto).toList();
    }

    public Client retrieveClientDetails(Integer clientId) {
        return findClientOrThrow(clientId);
    }

    public List<Record> retrieveLoggedInUserActivities(HttpSession session, Integer page) {
        Client loggedUser = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
        PageRequest nextPage = PageRequest.of(page, Constants.PAGE_SIZE);

        return postRepository.findAllByEnrolleesContains(loggedUser, nextPage).stream().map((Post post) -> {
            if (post instanceof Announcement casted) {
                return new AnnouncementDTO(new PostDTO(post), casted.getTitle(), casted.getDescription(),
                        casted.getEnrolled());
            } else if (post instanceof Meeting casted) {
                return new MeetingDTO(new PostDTO(post), casted.getTitle(), casted.getDescription(),
                        casted.getEnrolled(), casted.getPersonQuota(), new DateTimeDTO(casted.getMeetingDate()));
            } else {
                Event casted = (Event) post;
                return new EventDTO(new PostDTO(post), casted.getTitle(), casted.getDescription(),
                        casted.getEnrolled(), casted.getPersonQuota(), casted.getSchedule(),
                        new DateTimeDTO(casted.getStartDate()), new DateTimeDTO(casted.getEndDate()),
                        casted.getPicture());
            }
        }).toList();
    }

    public boolean isLoggedUserAuthorOfPost(HttpSession session, Integer postId) {
        String loggedUserEmail = SessionManager.retrieveEmailOrThrow(session);
        Post foundPost = findPostOrThrow(postId);
        return foundPost.getAuthor().getEmail().equals(loggedUserEmail);
    }

    public List<Client> getEnrolleesOfPost(Integer postId, Integer page) {
        Post foundPost = findPostOrThrow(postId);
        PageRequest nextPage = PageRequest.of(page, Constants.PAGE_SIZE);
        return foundPost.getEnrollees().stream().skip(page * Constants.PAGE_SIZE).limit(Constants.PAGE_SIZE).toList();
    }

    public Boolean createClientAccount(HttpSession session) {
        String email = SessionManager.retrieveEmailOrThrow(session);
        String givenName = SessionManager.retrieveGivenNameOrThrow(session);
        String familyName = SessionManager.retrieveFamilyNameOrThrow(session);
        String pictureUrl = SessionManager.retrievePictureOrThrow(session);

        if (clientRepository.existsByEmail(email)) {
            return false;
        } else {
            clientRepository.save(new Client(email, givenName, familyName, pictureUrl));
            return true;
        }
    }

    public Client deleteClientAccount(HttpSession session) {
        String authenticatedEmail = SessionManager.retrieveEmailOrThrow(session);
        Client foundClient = findClientOrThrow(authenticatedEmail);

        List<Post> enrolledInPosts = postRepository.findAllByEnrolleesContains(foundClient);
        List<Post> foundActiveAuthoredPosts = postRepository.findAllByAuthorEmailIsAndIsActiveIs(authenticatedEmail, true);

        enrolledInPosts.forEach((Post p) -> {
            Integer enrolled = p.getEnrolled();
            Set<Client> enrollees = p.getEnrollees();
            enrollees.remove(foundClient);
            p.setEnrollees(enrollees);
            p.setEnrolled(enrolled - 1);
        });

        foundActiveAuthoredPosts.forEach((Post p) -> p.setIsActive(false));

        postRepository.saveAll(enrolledInPosts);
        postRepository.saveAll(foundActiveAuthoredPosts);

        foundClient.setFirstName("Removed");
        foundClient.setLastName("Removed");
        foundClient.setEmail("removed@removed.com");
        foundClient.setIsDeleted(true);
        return clientRepository.save(foundClient);
    }

    public List<Category> retrieveClientCategories(HttpSession session) {
        return categoryRepository.findByClients_Email(SessionManager.retrieveEmailOrThrow(session));
    }

    public List<Category> retrieveClientCategories(Integer clientId) {
        return categoryRepository.findByClients_Id(clientId);
    }

    public List<Category> updateClientCategories(HttpSession session, CategoryListDTO updatedCategoriesDTO) {
        Client currentClient = findClientOrThrow(SessionManager.retrieveEmailOrThrow(session));
        List<Category> updatedCategories = findClientCategories(updatedCategoriesDTO.getCategories());
        currentClient.setInterests(new HashSet<>(updatedCategories));
        clientRepository.save(currentClient);
        return updatedCategories;
    }

    public List<Category> findClientCategories(Set<Integer> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    public Client findClientOrThrow(String clientEmail) {
        return clientRepository.findClientByEmail(clientEmail).orElseThrow(
                () -> new NoSuchElementException("A client with email: " + clientEmail + " does not exist."));
    }

    public Client findClientOrThrow(Integer clientId) {
        return clientRepository.findById(clientId).orElseThrow(
                () -> new NoSuchElementException("A client with id: " + clientId + " does not exist."));
    }

    public Post findPostOrThrow(Integer postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("A post with id: " + postId + " does not exist."));
    }
}
