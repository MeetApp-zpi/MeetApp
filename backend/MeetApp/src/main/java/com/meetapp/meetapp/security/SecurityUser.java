package com.meetapp.meetapp.security;

import com.meetapp.meetapp.service.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@Controller
public class SecurityUser {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/loginSuccess")
    public String getLoginInfo(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity<Map> response = restTemplate
                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();
            userAttributes.isEmpty();
        }
        // In current setup it's impossible to forward routing to / (root).
        //
        return "forward:/";
    }

    @GetMapping("/verifyTokenBearer")
    public String verifyTokenBearer(HttpSession sess, HttpServletRequest request) throws GeneralSecurityException, IOException {
        String cookie = request.getHeader("Authorization");
        if (JwtValidator.verify(cookie)) {
            SecurityContextImpl cont = new SecurityContextImpl();
            OidcIdToken oidcIdToken = new OidcIdToken("12123");
            DefaultOidcUser defaultUser = new DefaultOidcUser();
            OAuth2AuthenticationToken oAuthToken = new OAuth2AuthenticationToken();
            oAuthToken.setAuthenticated(true);
            cont.setAuthentication(oAuthToken);
            sess.setAttribute("SPRING_SECURITY_CONTEXT", cont);
        }
        return "";
    }
}
