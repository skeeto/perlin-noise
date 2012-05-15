package com.nullprogram.lwjgl;

import com.nullprogram.guide.Arch;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import lombok.SneakyThrows;
import org.lwjgl.BufferUtils;
import static com.nullprogram.guide.NativeGuide.prepare;

/**
 * Prepares all of the LWJGL native binaries for loading.
 */
public final class Lwjgl {

    /** Byte array read buffer size. */
    private static final int BUFFER_SIZE = 1024;

    /** True if lwjgl has been configured, so it's only done once. */
    private static boolean isSetup = false;

    /** Hidden constructor. */
    private Lwjgl() {
    }

    /**
     * Prepares all of the LWJGL native libraries for loading.
     * @throws java.io.IOException when the libraries could not be loaded
     */
    public static synchronized void setup() throws IOException {
        if (!isSetup) {
            prepare(Arch.LINUX_64, "/libjinput-linux64.so");
            prepare(Arch.LINUX_32, "/libjinput-linux.so");
            prepare(Arch.LINUX_64, "/liblwjgl64.so");
            prepare(Arch.LINUX_32, "/liblwjgl.so");
            prepare(Arch.LINUX_64, "/libopenal64.so");
            prepare(Arch.LINUX_32, "/libopenal.so");
            prepare(Arch.MAC_64, "/libjinput-osx.jnilib");
            prepare(Arch.MAC_64, "/liblwjgl.jnilib");
            prepare(Arch.MAC_64, "/openal.dylib");
            prepare(Arch.WINDOWS_64, "/jinput-dx8_64.dll");
            prepare(Arch.WINDOWS_32, "/jinput-dx8.dll");
            prepare(Arch.WINDOWS_64, "/jinput-raw_64.dll");
            prepare(Arch.WINDOWS_32, "/jinput-raw.dll");
            prepare(Arch.WINDOWS_64, "/lwjgl64.dll");
            prepare(Arch.WINDOWS_32, "/lwjgl.dll");
            prepare(Arch.WINDOWS_32, "/OpenAL32.dll");
            prepare(Arch.WINDOWS_64, "/OpenAL64.dll");
            isSetup = true;
        }
    }

    /**
     * Make a new direct FloatBuffer based on a float array.
     * @param in  the float array to be wrapped
     * @return a direct FloatBuffer
     */
    public static FloatBuffer toBuffer(final float[] in) {
        FloatBuffer buf = BufferUtils.createFloatBuffer(in.length).put(in);
        buf.rewind();
        return buf;
    }

    /**
     * Make a new direct DoubleBuffer based on a byte array.
     * @param in  the byte array to be wrapped
     * @return a direct DoubleBuffer
     */
    public static DoubleBuffer toBuffer(final double[] in) {
        DoubleBuffer buf = BufferUtils.createDoubleBuffer(in.length).put(in);
        buf.rewind();
        return buf;
    }

    /**
     * Read the given input into a byte array.
     * @param in  the input stream to be read
     * @return the byte array containing the resource
     */
    @SneakyThrows
    public static String fetch(final InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int n;
        while ((n = in.read(buffer)) > 0) {
            out.write(buffer, 0, n);
        }
        out.close();
        return new String(out.toByteArray());
    }
}
