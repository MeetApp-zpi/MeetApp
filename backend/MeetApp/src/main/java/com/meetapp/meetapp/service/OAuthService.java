package com.meetapp.meetapp.service;

import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.repository.ClientRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OAuthService {
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final ClientRepository clientRepository;

    public OAuthService(OAuth2AuthorizedClientService authorizedClientService, ClientRepository clientRepository) {
        this.authorizedClientService = authorizedClientService;
        this.clientRepository = clientRepository;
    }

    public Client createFromToken(OAuth2AuthenticationToken authToken) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authToken.getAuthorizedClientRegistrationId(),
                        authToken.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                .getTokenValue());
        HttpEntity entity = new HttpEntity("", headers);
        ResponseEntity<Map> response = restTemplate
                .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
        Map<String, String> userAttributes = response.getBody();
        String email = userAttributes.get("email");
        return clientRepository.findClientByEmail(email);
    }
}
