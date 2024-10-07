package org.example;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // Creare un'istanza di NetHttpTransport
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            // Ottenere le credenziali
            Credential credential = GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT);

            // Creare un'istanza di GmailService
            EmailService gmailService = new GmailService(credential);

            // Creare un'istanza di ConcreteEmailSender
            EmailSender emailSender = new GmailEmailSender(gmailService);

            // Inviare l'email di prova
            String recipientEmail = "fragrassi825@gmail.com"; // Cambia con il tuo indirizzo email
            String subject = "Email di prova";
            String body = "Sei gnocca";

            // Definire i percorsi dei file
            String[] fileNames = new String[] {"test.txt", "test2.txt"};
            InputStream[] inputStreams = new InputStream[fileNames.length];
            File[] attachments = new File[fileNames.length];

            for (int i = 0; i < fileNames.length; i++) {
                // Crea un File object per ogni file
                attachments[i] = new File(Paths.get("src\\main\\file", fileNames[i]).toAbsolutePath().toString());

                // Crea un InputStream per ogni file
                try (FileInputStream fis = new FileInputStream(attachments[i])) {
                    // Leggi i dati dal FileInputStream e crea un ByteArrayInputStream
                    byte[] fileData = fis.readAllBytes();
                    inputStreams[i] = new ByteArrayInputStream(fileData);
                }
            }

            // Invia l'email con gli allegati
            emailSender.send(recipientEmail, subject, body, attachments, inputStreams, fileNames);

            String htmlBody = "<html><body><h1>Titolo</h1><p>Questo è il contenuto in HTML.</p></body></html>";
            emailSender.send(recipientEmail, subject, htmlBody, attachments, inputStreams, fileNames);


            System.out.println("Email inviata con successo!");

            // Non è necessario chiudere gli InputStream qui, poiché abbiamo già creato ByteArrayInputStream

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
