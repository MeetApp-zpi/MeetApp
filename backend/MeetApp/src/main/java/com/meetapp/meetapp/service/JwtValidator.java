package com.meetapp.meetapp.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import lombok.val;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class JwtValidator {
    public static boolean verify(String token) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier jwtTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .build();

        val googleToken = jwtTokenVerifier.verify(token);
        return googleToken != null;
    }
}
