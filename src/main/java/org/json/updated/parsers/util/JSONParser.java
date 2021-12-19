package org.json.updated.parsers.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings ("ResultOfMethodCallIgnored")
public final class JSONParser {
    public static String get(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:94.0) Gecko/20100101 Firefox/94.0");

            return convert(new BufferedInputStream(connection.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String post(String url, String data) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "DiscordBot (v1.0.0, http://github.com)");
            connection.setRequestMethod("POST");

            OutputStream stream = connection.getOutputStream();
            stream.write(data.getBytes(StandardCharsets.UTF_8));
            stream.close();

            connection.disconnect();

            return convert(new BufferedInputStream(connection.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String sendFile(String url, File file) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

            String boundary = "===" + System.currentTimeMillis() + "===";

            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.setRequestProperty("User-Agent", "DiscordBot (v1.0.0, http://github.com)");
            connection.setReadTimeout(5000);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            MultipartForm form = new MultipartForm(boundary, connection.getOutputStream());
            form.addFile("fileName", file);
            form.end();

            connection.disconnect();

            return convert(new BufferedInputStream(connection.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Matcher getValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^,]*)\",");
        Matcher matcher = pattern.matcher(json);
        matcher.find(); // DO NOT REMOVE THIS I HATE JAVA SO MUCH
        return matcher;
    }

    private static String convert(InputStream stream) {
        Scanner scanner = new Scanner(stream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "/";
    }
}
