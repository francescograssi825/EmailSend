package org.example;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;




public class GmailService implements EmailService {
    private final Gmail gmailService;

    public GmailService(Credential credential) throws GeneralSecurityException, IOException {
        this.gmailService = new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(), // Usa GsonFactory
                credential)
                .setApplicationName("Gmail API Java Quickstart")
                .build();    }

    @Override
    public void send(String to, String subject, String body, File[] attachments, InputStream[] inputStreams, String[] fileNames) throws MessagingException, IOException {
        MimeMessage email = createEmailWithAttachment(to, subject, body, attachments, inputStreams, fileNames);
        Message message = createMessageWithEmail(email);
        gmailService.users().messages().send("me", message).execute();
    }


        public MimeMessage createEmailWithAttachment(String to, String subject, String bodyContent, File[] attachments, InputStream[] inputStreams, String[] fileNames) throws MessagingException, IOException {
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("musicvirusapp@gmail.com")); // Sostituisci con il tuo indirizzo email
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            // Crea il corpo dell'email e gli allegati
            MimeMultipart multipart = createMultipart(bodyContent, attachments, inputStreams, fileNames);

            // Imposta il contenuto dell'email
            message.setContent(multipart);

            return message;
        }

        // Funzione per creare il multipart (corpo + allegati)
        private MimeMultipart createMultipart(String bodyContent, File[] attachments, InputStream[] inputStreams, String[] fileNames) throws MessagingException, IOException {
            MimeMultipart multipart = new MimeMultipart();

            // Aggiungi la parte del corpo dell'email
            multipart.addBodyPart(createBodyPart(bodyContent));

            // Aggiungi gli allegati InputStream o File
            if (inputStreams != null && fileNames != null) {
                addInputStreamAttachments(multipart, inputStreams, fileNames);
            } else if (attachments != null) {
                addFileAttachments(multipart, attachments);
            }

            return multipart;
        }

        // Funzione per creare la parte del corpo dell'email
        private MimeBodyPart createBodyPart(String bodyContent) throws MessagingException {
            MimeBodyPart bodyPart = new MimeBodyPart();

            // Controlla se il bodyContent contiene tag HTML
            if (bodyContent.trim().startsWith("<") && bodyContent.trim().endsWith(">")) {
                // Se contiene tag HTML, imposta il contenuto come HTML
                bodyPart.setContent(bodyContent, "text/html; charset=utf-8");
            } else {
                // Altrimenti, imposta come testo semplice
                bodyPart.setText(bodyContent);
            }

            return bodyPart;
        }

        // Funzione per aggiungere allegati tramite InputStream
        private void addInputStreamAttachments(MimeMultipart multipart, InputStream[] inputStreams, String[] fileNames) throws MessagingException, IOException {
            for (int i = 0; i < inputStreams.length; i++) {
                if (inputStreams[i] != null) {
                    MimeBodyPart inputStreamPart = new MimeBodyPart();
                    DataSource dataSource = new ByteArrayDataSource(inputStreams[i], "application/octet-stream");
                    inputStreamPart.setDataHandler(new DataHandler(dataSource));
                    inputStreamPart.setFileName(fileNames[i]);
                    multipart.addBodyPart(inputStreamPart);
                }
            }
        }

        // Funzione per aggiungere allegati tramite File
        private void addFileAttachments(MimeMultipart multipart, File[] attachments) throws MessagingException, IOException {
            for (File attachment : attachments) {
                if (attachment != null && attachment.exists()) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(attachment);
                    multipart.addBodyPart(attachmentPart);
                } else {
                    System.out.println("Attachment does not exist: " + (attachment != null ? attachment.getAbsolutePath() : "null"));
                }
            }
        }




    private Message createMessageWithEmail(MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = java.util.Base64.getUrlEncoder().encodeToString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}




