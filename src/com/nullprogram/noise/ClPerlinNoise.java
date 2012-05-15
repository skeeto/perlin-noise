package com.nullprogram.noise;

import com.nullprogram.lwjgl.Lwjgl;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;
import lombok.extern.java.Log;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.CL;
import org.lwjgl.opencl.CLCommandQueue;
import org.lwjgl.opencl.CLContext;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLKernel;
import org.lwjgl.opencl.CLMem;
import org.lwjgl.opencl.CLPlatform;
import org.lwjgl.opencl.CLProgram;
import org.lwjgl.opencl.Util;
import static org.lwjgl.opencl.CL10.*;

@Log
public class ClPerlinNoise {

    static {
        try {
            Lwjgl.setup();
            CL.create();
        } catch (IOException e) {
            log.severe("Could not setup lwjgl: " + e);
        } catch (LWJGLException e) {
            log.severe("Could not setup OpenCL: " + e);
        }
    }

    private static final String SOURCE =
        Lwjgl.fetch(ClPerlinNoise.class.getResourceAsStream("perlin3d.cl"));

    private final int width;
    private final int height;
    private final float step;

    private final FloatBuffer gradients;
    private final FloatBuffer params;
    private final FloatBuffer values;
    private final CLMem gradientsBuf;
    private final CLMem paramsBuf;
    private final CLMem valuesBuf;

    private final CLPlatform platform;
    private final CLContext context;
    private final CLCommandQueue queue;
    private final CLProgram program;
    private final CLKernel kernel;

    public ClPerlinNoise(int seed, int width, int height, float step)
        throws LWJGLException {

        this.width = width;
        this.height = height;
        this.step = step;

        /* Prepare gradients. */
        int gsize = (width + 1) * (height + 1);
        int size = (int) (width / step * height / step);
        Random rng = new Random(seed);
        float[] vectors = new float[3 * gsize];
        for (int i = 0; i < vectors.length; i++) {
            vectors[i] = rng.nextFloat() * 2f - 1f;
        }
        gradients = Lwjgl.toBuffer(vectors);
        values = BufferUtils.createFloatBuffer(size);
        params = BufferUtils.createFloatBuffer(4);
        params.put(0, 0f); // z
        params.put(1, width);
        params.put(2, height);
        params.put(3, step);

        /* Init OpenCL */
        platform = CLPlatform.getPlatforms().get(0);
        List<CLDevice> devices = platform.getDevices(CL_DEVICE_TYPE_GPU);
        context = CLContext.create(platform, devices, null, null, null);
        queue = clCreateCommandQueue(context, devices.get(0),
                                     CL_QUEUE_PROFILING_ENABLE, null);

        /* Prepare buffers. */
        gradientsBuf
            = clCreateBuffer(context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                             gradients, null);
        clEnqueueWriteBuffer(queue, gradientsBuf, 1, 0, gradients, null, null);
        paramsBuf
            = clCreateBuffer(context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                             params, null);
        clEnqueueWriteBuffer(queue, paramsBuf, 1, 0, params, null, null);
        valuesBuf
            = clCreateBuffer(context, CL_MEM_WRITE_ONLY | CL_MEM_COPY_HOST_PTR,
                             values, null);
        clFinish(queue);

        /* Prepare program. */
        program = clCreateProgramWithSource(context, SOURCE, null);
        Util.checkCLError(clBuildProgram(program, devices.get(0), "", null));
        kernel = clCreateKernel(program, "perlin3d", null);
        kernel.setArg(0, gradientsBuf);
        kernel.setArg(1, paramsBuf);
        kernel.setArg(2, valuesBuf);
    }

    public float[][] sample(float z) {
        params.put(0, z);
        clEnqueueWriteBuffer(queue, paramsBuf, 1, 0, params, null, null);
        clFinish(queue);

        PointerBuffer work = BufferUtils.createPointerBuffer(1);
        work.put(0, values.capacity());
        Util.checkCLError(clEnqueueNDRangeKernel(queue, kernel, 1, null,
                                                 work, null, null, null));

        /* Gather results. */
        clEnqueueReadBuffer(queue, valuesBuf, 1, 0, values, null, null);
        clFinish(queue);

        int wwidth = (int) (width / step);
        int wheight = (int) (height / step);
        float[][] results = new float[wwidth][wheight];
        values.rewind();
        for (int y = 0; y < wwidth; y++) {
            for (int x = 0; x < wheight; x++) {
                results[x][y] = values.get();
                System.out.printf("%5.2f ", results[x][y]);
            }
            System.out.println();
        }
        return results;
    }

    public void dispose() {
        clReleaseKernel(kernel);
        clReleaseMemObject(gradientsBuf);
        clReleaseMemObject(paramsBuf);
        clReleaseMemObject(valuesBuf);
        clReleaseProgram(program);
        clReleaseCommandQueue(queue);
        clReleaseContext(context);
    }
}
