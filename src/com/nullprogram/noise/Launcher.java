package com.nullprogram.noise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.SneakyThrows;
import lombok.val;

public final class Launcher {

    @SneakyThrows
    public static void main(String[] args) {
        int size = 32;
        int depth = 4;
        float step = 0.04f;
        //Runnable calc = new ClSlice3d(size, size, depth, step);
        Runnable calc = new Slice3d(size, size, depth, step);
        System.out.println(time(calc) + " seconds");
        System.exit(0);
    }

    private static double time(Runnable r) {
        long start = System.nanoTime();
        r.run();
        long end = System.nanoTime();
        return (end - start) / 1000000000.0;
    }
}
