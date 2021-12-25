package org.json.updated.parsers;

import org.json.updated.parsers.util.FileSystemHelper;

public interface JSONObject {
    String sep = FileSystemHelper.getSeparator();
    String mcFolder = FileSystemHelper.getMinecraftFolder();
    String home = System.getProperty("user.home");

    void handle();

    String getName();
}
