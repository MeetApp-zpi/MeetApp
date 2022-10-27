package com.meetapp.meetapp.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Optional;

public class SessionManager {
    public static Boolean isAuthenticated(HttpSession sess) {
        SecurityContextImpl context = (SecurityContextImpl) sess.getAttribute("SPRING_SECURITY_CONTEXT");

        if (context == null) {
            return false;
        }

        return context.getAuthentication().isAuthenticated();
    }

    public static Optional<String> retrieveEmail(HttpSession sess) {
        Boolean isAuthenticated = isAuthenticated(sess);

        if (!isAuthenticated) {
            return Optional.empty();
        }

        SecurityContextImpl context = (SecurityContextImpl) sess.getAttribute("SPRING_SECURITY_CONTEXT");
        DefaultOidcUser userObj = (DefaultOidcUser) context.getAuthentication().getPrincipal();
        return Optional.ofNullable(userObj.getEmail());
    }

    public static String retrieveEmailOrThrow(HttpSession sess) {
        return retrieveEmail(sess).orElseThrow(() -> new SecurityException("User is not authenticated"));
    }
}
