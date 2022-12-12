package com.meetapp.meetapp.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtValidatorTest {

    @Test
    public void verify() throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier tokenVer = mock(GoogleIdTokenVerifier.class);
        GoogleIdTokenVerifier.Builder builder = mock(GoogleIdTokenVerifier.Builder.class);
        when(builder.build()).thenReturn(new GoogleIdTokenVerifier(new NetHttpTransport(),
                new GsonFactory()));

        when(tokenVer.verify(anyString())).thenReturn(null);
        JwtValidator.verify("testtoken");
    }

    @Test
    public void retrieveClientFromToken() throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier tokenVerifier = mock(GoogleIdTokenVerifier.class);
        GoogleIdToken idToken = mock(GoogleIdToken.class);

        when(tokenVerifier.verify("123")).thenReturn(idToken);
        when(idToken.getPayload()).thenReturn(new GoogleIdToken.Payload());

        JwtValidator.retrieveClientFromToken("123");
    }
}
