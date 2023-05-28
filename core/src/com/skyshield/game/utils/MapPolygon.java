package com.skyshield.game.utils;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MapPolygon {
    public float[] vertices;
    private BufferedImage image;
    private int radius;

    public MapPolygon(String src) {
        try{
            this.image = ImageIO.read(new File(src));
            this.radius = Math.max(image.getWidth(), image.getHeight());
            this.vertices = getVerticesCoords();
        }catch(IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }


    private float[] getVerticesCoords() {
        vertices = new float[720];
        ArrayList<Float> list = new ArrayList<>();
        float[] xy;
        for(int i = 0; i < 360; i++) {
            xy = getVertex(i);
            list.add(xy[0]);
            list.add(xy[1]);
        }
        for(int i = 0; i < 720; i++) {
            vertices[i] = list.get(i);
        }
        System.out.println(image.getHeight());
        return vertices;
    }

    private float[] getVertex(int degree) {
        int x, y;
        for(int i = radius; i > 0; i--) {
            x = (int) (i*Math.sin(Math.toRadians(degree)))+image.getWidth()/2;
            y = (int) (i*Math.cos(Math.toRadians(degree)))+image.getHeight()/2;
            try{
                if(image.getRGB(x, y)==Color.BLACK.getRGB()) {
                    System.out.println("degree: "+degree+"\nx: "+x+"\ny: "+y);
                    return new float[]{x, y};
                }
            }catch (Exception ignored) {
            }
        }
        return new float[]{0, 0};
    }
}
