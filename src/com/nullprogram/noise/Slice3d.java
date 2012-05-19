package com.nullprogram.noise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

@RequiredArgsConstructor
public class Slice3d implements Runnable {

    private final int width;
    private final int height;
    private final int depth;
    private final float step;

    @Override
    @SneakyThrows
    public void run() {
        val im = new BufferedImage((int) (width / step), (int) (height / step),
                                   BufferedImage.TYPE_INT_RGB);
        Noise noise = new PerlinNoise(0, 3);
        int count = 0;
        for (float z = 0; z < depth; z += step, count++) {
            for (int i = 0; i < im.getWidth(); i++) {
                double x = i * step;
                for (int j = 0; j < im.getHeight(); j++) {
                    double y = j * step;
                    Vector p = new Vector(x, y, z);
                    double m = noise.sample(p);
                    float v = (float) ((m + 2f / 3f) * 2f / 3f);
                    int c = new Color(v, v, v).getRGB();
                    im.setRGB(i, j, c);
                }
            }
            String name = String.format("simple-%06d.png", count);
            System.out.println(name + " (" + z + ")");
            ImageIO.write(im, "PNG", new File(name));
        }
    }
}
