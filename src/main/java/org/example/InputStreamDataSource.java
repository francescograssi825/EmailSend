package org.example;

import javax.activation.DataSource;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

public class InputStreamDataSource implements DataSource {
    private final InputStream inputStream;
    private final String fileName;

    public InputStreamDataSource(InputStream inputStream, String fileName) {
        this.inputStream = inputStream;
        this.fileName = fileName;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public String getContentType() {
        return "application/octet-stream"; // Puoi specificare il tipo di contenuto corretto
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Output stream is not supported.");
    }
}
