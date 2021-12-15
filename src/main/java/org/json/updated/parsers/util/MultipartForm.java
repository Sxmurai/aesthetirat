package org.json.updated.parsers.util;

import java.io.*;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

class MultipartForm {
    private static final String NEWLINE = "\r\n";

    private final String boundary;
    private final PrintWriter writer;
    private final OutputStream outputStream;

    public MultipartForm(String boundary, OutputStream outputStream) {
        this.boundary = boundary;
        this.outputStream = outputStream;
        this.writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
    }

    public void addFile(String name, File file) {
        this.writer.append("--").append(this.boundary).append(NEWLINE);
        this.writer.append("Content-Disposition: form-data; name=\"").append(name).append("\"; filename=\"").append(file.getName()).append("\"").append(NEWLINE);
        this.writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(file.getName())).append(NEWLINE);
        this.writer.append("Content-Transfer-Encoding: binary").append(NEWLINE);
        this.writer.append(NEWLINE);
        this.writer.flush();

        try {
            FileInputStream stream = new FileInputStream(file);

            byte[] buffer = new byte[4096];
            int i;
            while ((i = stream.read(buffer)) != -1) this.outputStream.write(buffer, 0, i);

            this.outputStream.flush();
            stream.close();

            this.writer.append(NEWLINE);
            this.writer.flush();
        } catch (Exception ignored) {
        }
    }

    // it's already written to the stream, so we should be good.
    public void end() {
        this.writer.append(NEWLINE).flush();
        this.writer.append("--").append(this.boundary).append("--").append(NEWLINE);
        this.writer.close();
    }
}
