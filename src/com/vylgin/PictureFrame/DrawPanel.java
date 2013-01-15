package com.vylgin.PictureFrame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {

    private BufferedImage image;
    private int width, height;

    public DrawPanel() {
        try {
            image = ImageIO.read(new File("images/default.png"));
        } catch (IOException e) {
        }

        width = 320;
        height = 240;
    }

    public void openImage(File file) {
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
        }
    }

    public void setSizeImage(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, width, height, null);
    }
}