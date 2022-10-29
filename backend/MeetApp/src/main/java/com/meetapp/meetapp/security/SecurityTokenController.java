package com.meetapp.meetapp.security;

import com.meetapp.meetapp.service.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class SecurityTokenController {

    @GetMapping("/api/verifyTokenBearer")
    public String verifyTokenBearer(HttpSession sess, HttpServletRequest request) throws GeneralSecurityException, IOException {
        String cookie = request.getHeader("Authorization");
        String onlyToken = cookie.split(" ")[1];
        if (JwtValidator.verify(onlyToken)) {
            SecurityContextImpl cont = new SecurityContextImpl();
            HashMap<String, Object> claims = new HashMap<>();
            String email = JwtValidator.retrieveClientFromToken(onlyToken);
            claims.put("sub", "42342");
            claims.put("given_name", "XXXX");
            claims.put("family_name", "YYYY");
            claims.put("email", email);
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