package org.example;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

public class GoogleAuthorizeUtil {

    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Assicurati che il percorso sia corretto e che il file esista
        InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream("/client_secret_165662255285-itpm42gsjq31l85u04qicmu3cmr76eli.apps.googleusercontent.com.json");
        if (in == null) {
            throw new FileNotFoundException("Resource file 'client_secret.json' not found");
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

        // Costruzione del flusso di autorizzazione e la gestione delle credenziali
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JacksonFactory.getDefaultInstance(), clientSecrets, Collections.singletonList(GmailScopes.GMAIL_SEND))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens"))) // Directory per i token
                .setAccessType("offline")
                .build();

        // Inizializza il ricevitore locale sulla porta 8888
        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                .setPort(8888)  // Assicurati che sia 8888
                .build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}
