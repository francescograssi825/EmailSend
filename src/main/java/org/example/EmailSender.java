package org.example;

import java.io.File;
import java.io.InputStream;

// Classe astratta che funge da bridge
public abstract class EmailSender {
    protected EmailService emailService;

    protected EmailSender(EmailService emailService) {
        this.emailService = emailService;
    }

    public abstract void send(String to, String subject, String body, File[] attachments, InputStream[] inputStreams, String[] fileNames) throws Exception;
}