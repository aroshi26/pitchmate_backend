package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@Component
public class FirebaseInitializer {

    @PostConstruct
    public void initialize() {
        try {
            String firebaseCredsBase64 = System.getenv("FIREBASE_CREDENTIALS_BASE64");

            if (firebaseCredsBase64 == null || firebaseCredsBase64.isEmpty()) {
                System.err.println("FIREBASE_CREDENTIALS_BASE64 environment variable is not set.");
                return;
            }

            byte[] decodedBytes = Base64.getDecoder().decode(firebaseCredsBase64);
            InputStream serviceAccountStream = new ByteArrayInputStream(decodedBytes);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase has been initialized successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
