package org.example;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface EmailService {
    void send(String to, String subject, String body, File[] attachments, InputStream[] inputStreams, String[] fileNames) throws MessagingException, IOException;
}
