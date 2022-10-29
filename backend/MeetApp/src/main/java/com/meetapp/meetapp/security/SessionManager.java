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

    public static Optional<String> retrieveAttribute(HttpSession sess, String attribute) {
        Boolean isAuthenticated = isAuthenticated(sess);

        if (!isAuthenticated) {
            return Optional.empty();
        }

        SecurityContextImpl context = (SecurityContextImpl) sess.getAttribute("SPRING_SECURITY_CONTEXT");
        DefaultOidcUser userObj = (DefaultOidcUser) context.getAuthentication().getPrincipal();
        return Optional.ofNullable(userObj.getAttribute(attribute));
    }

    public static String retrieveAttributeOrThrow(HttpSession sess, String attribute) {
        return retrieveAttribute(sess, attribute).orElseThrow(() -> new SecurityException("User is not authenticated"));
    }
}
