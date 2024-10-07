package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    // Metodo per creare un File a partire da un InputStream
    public static File createFileFromInputStream(InputStream inputStream, String fileName) throws IOException {
        // Creare un file temporaneo
        File tempFile = File.createTempFile(fileName, null);
        tempFile.deleteOnExit(); // Elimina il file temporaneo alla chiusura dell'applicazione

        // Scrivere l'InputStream nel file
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }
}
