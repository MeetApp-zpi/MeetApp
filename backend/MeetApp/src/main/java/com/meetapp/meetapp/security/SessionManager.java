package com.meetapp.meetapp.security;

import jakarta.servlet.http.HttpSession;
import org.apache.http.auth.AuthenticationException;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

public class SessionManager {
    public static Boolean isAuthenticated(HttpSession sess) {
        SecurityContextImpl context = (SecurityContextImpl) sess.getAttribute("SPRING_SECURITY_CONTEXT");

        if (context == null) {
            return false;
        }

        return context.getAuthentication().isAuthenticated();
    }

    public static String retrieveEmailOrThrow(HttpSession sess) throws AuthenticationException {
        Boolean isAuthenticated = isAuthenticated(sess);

        if (!isAuthenticated) {
            throw new AuthenticationException("Unauthorized");
        }

        SecurityContextImpl context = (SecurityContextImpl) sess.getAttribute("SPRING_SECURITY_CONTEXT");
        DefaultOidcUser userObj = (DefaultOidcUser) context.getAuthentication().getPrincipal();
        return userObj.getEmail();
    }
}
