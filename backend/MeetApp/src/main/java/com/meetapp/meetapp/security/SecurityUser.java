package com.meetapp.meetapp.security;

import com.meetapp.meetapp.service.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        String onlyToken = cookie.split(" ")[1];
        if (JwtValidator.verify(onlyToken)) {
            SecurityContextImpl cont = new SecurityContextImpl();
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("at_hash", "ZDE42342434");
            claims.put("sub", "4234141414");
            claims.put("given_name", "Kajetan");
            claims.put("family_name", "Pynka");
            claims.put("email", "kyattpoland@gmail.com");
            OidcIdToken oidcIdToken = new OidcIdToken(onlyToken, Instant.now(), Instant.MAX, claims);
            OidcUserAuthority oidcUserAuthority = new OidcUserAuthority(oidcIdToken);
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(oidcUserAuthority);
            DefaultOidcUser defaultUser = new DefaultOidcUser(authorities, oidcIdToken);
            OAuth2AuthenticationToken oAuthToken = new OAuth2AuthenticationToken(defaultUser, authorities, "google");
            oAuthToken.setAuthenticated(true);
            cont.setAuthentication(oAuthToken);
            sess.setAttribute("SPRING_SECURITY_CONTEXT", cont);
        }
        return "";
    }
}
