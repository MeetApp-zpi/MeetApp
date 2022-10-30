package com.meetapp.meetapp.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Optional;

public class SessionManager {
    public static Boolean isAuthenticated(HttpSession session) {
        SecurityContextImpl context = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");

        if (context == null) {
            return false;
        }

        return context.getAuthentication().isAuthenticated();
    }

    private static Optional<String> retrieveAttribute(HttpSession session, String attribute) {
        Boolean isAuthenticated = isAuthenticated(session);

        if (!isAuthenticated) {
            return Optional.empty();
        }

        SecurityContextImpl context = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        DefaultOidcUser userObj = (DefaultOidcUser) context.getAuthentication().getPrincipal();
        return Optional.ofNullable(userObj.getAttribute(attribute));
    }

    public static String retrieveGivenNameOrThrow(HttpSession session) {
        return retrieveAttribute(session, "given_name").orElseThrow(() -> new SecurityException("User is not authenticated"));
    }

    public static String retrieveFamilyNameOrThrow(HttpSession session) {
        return retrieveAttribute(session, "family_name").orElseThrow(() -> new SecurityException("User is not authenticated"));
    }

    public static String retrievePictureOrThrow(HttpSession session) {
        return retrieveAttribute(session, "picture").orElseThrow(() -> new SecurityException("User is not authenticated"));
    }

    public static String retrieveEmailOrThrow(HttpSession session) {
        return retrieveAttribute(session, "email").orElseThrow(() -> new SecurityException("User is not authenticated"));
    }
}
