package com.au.glasgow.service;

import java.util.Collections;

import com.au.glasgow.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

@Service
public class TokenValidationService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    /**
     * verify the token validity
     *
     * @param userName
     * @param token
     * @return
     */
    public boolean isTokenValid(String userName, String token) {
        GoogleIdToken idTokenVerified = null;
        try {
            idTokenVerified = getTokenPayload(token);

        } catch (Exception ex) {
            return false;
        }
        return null != idTokenVerified ? idTokenVerified.getPayload().getEmail().equalsIgnoreCase(userName) : false;
    }

    /**
     * method to fetch the token payload
     *
     * @param token
     * @return
     * @throws Exception
     */
    public GoogleIdToken getTokenPayload(String token) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(clientId)).build();
        GoogleIdToken idTokenVerified = null;
        try {
            idTokenVerified = verifier.verify(token);

        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return idTokenVerified;
    }

    /**
     * This method fetch email from google token payload by calling google verify
     * token Api
     *
     * @param token
     * @return
     */
    public String getEmailFromToken(String token) {
        try {
            return getTokenPayload(token).getPayload().getEmail();

        } catch (Exception ex) {
            throw new InvalidTokenException();
        }
    }
}