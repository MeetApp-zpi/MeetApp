package com.meetapp.meetapp.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextImpl;

public class SessionManager {
    public static boolean isAuthenticated(HttpSession sess) {
        SecurityContextImpl context = (SecurityContextImpl) sess.getAttribute("SPRING_SECURITY_CONTEXT");

        if (context == null) {
            return false;
        }

        return context.getAuthentication().isAuthenticated();
    }
}
