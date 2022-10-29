package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.repository.ClientRepository;
import com.meetapp.meetapp.security.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClientAccount(HttpSession session) {
        String email = SessionManager.retrieveAttributeOrThrow(session, "email");
        String givenName = SessionManager.retrieveAttributeOrThrow(session, "given_name");
        String familyName = SessionManager.retrieveAttributeOrThrow(session, "family_name");

        String pictureUrl = SessionManager.retrieveAttributeOrThrow(session, "picture");
        Byte[] picture = downloadPictureOrThrow(pictureUrl);

        if (clientRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with this e-mail address already exists");
        }

        return clientRepository.save(new Client(email, givenName, familyName, picture));
    }

    public Client deleteClientAccount(Integer clientId, HttpSession session) {
        String authenticatedEmail = SessionManager.retrieveAttributeOrThrow(session, "email");
        Client foundClient = findClientOrThrow(clientId);

        if (Objects.equals(foundClient.getEmail(), authenticatedEmail)) {
            foundClient.setEvents(null);
            foundClient.setMeetings(null);
            foundClient.setInterests(null);
            foundClient.setFirstName("Removed");
            foundClient.setLastName("Removed");
            foundClient.setEmail("Removed");
            foundClient.setIsDeleted(true);
            return clientRepository.save(foundClient);
        } else {
            throw new SecurityException("A client with email: " + authenticatedEmail + " cannot delete user with id: " + clientId);
        }
    }

    public Client findClientOrThrow(Integer clientId) {
        return clientRepository.findById(clientId).orElseThrow(
                () -> new NoSuchElementException("A client with id: " + clientId + " does not exist."));
    }

    public Byte[] downloadPictureOrThrow(String pictureUrl) {
        try {
            URL urlObj = new URL(pictureUrl);
            BufferedImage bufferedImage = ImageIO.read(urlObj);
            WritableRaster raster = bufferedImage.getRaster();
            DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
            byte[] dataBytes = data.getData();
            Byte[] byteObjects = new Byte[dataBytes.length];

            int i = 0;
            for (byte b : dataBytes) {
                byteObjects[i++] = b;
            }

            return byteObjects;
        } catch (IOException e) {
            throw new RuntimeException("Failed to download image from: " + pictureUrl);
        }
    }
}
