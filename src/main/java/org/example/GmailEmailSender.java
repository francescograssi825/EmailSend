package org.example;


import java.io.File;
import java.io.InputStream;

public class GmailEmailSender extends EmailSender {

    public GmailEmailSender(EmailService emailService) {
        super(emailService);
    }

    @Override
    public void send(String to, String subject, String body, File[] attachments, InputStream[] inputStreams, String[] fileNames) throws Exception {
        emailService.send(to, subject, body, attachments, inputStreams, fileNames);
    }
}