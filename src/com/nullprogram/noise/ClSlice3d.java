package com.nullprogram.noise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

@RequiredArgsConstructor
public class ClSlice3d implements Runnable {

    private static final float SCALE = 2f / 3f;

    private final int width;
    private final int height;
    private final int depth;
    private final float step;

    @Override
    @SneakyThrows
    public void run() {
        ClPerlinNoise cl = new ClPerlinNoise(0, step, width, height, depth);
        val im = new BufferedImage((int) (width / step), (int) (height / step),
                                   BufferedImage.TYPE_INT_RGB);
        int count = 0;
        for (float z = 0; z < depth; z += step, count++) {
            float[][] results = cl.sample(z);
            for (int y = 0; y < results[0].length; y++) {
                for (int x = 0; x < results.length; x++) {
                    float v = ((results[x][y] + SCALE) * SCALE);
                    v = Math.max(Math.min(v, 1f), 0f);
                    int c = new Color(v, v, v).getRGB();
                    im.setRGB(x, y, c);
                }
            }
            String name = String.format("opencl-%06d.png", count);
            System.out.println(name + " (" + z + ")");
            ImageIO.write(im, "PNG", new File(name));
        }
    }
}
