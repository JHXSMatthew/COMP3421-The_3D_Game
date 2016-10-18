package game.utils;

import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Matthew on 30/09/2016.
 */
public class BufferUtils {

    public static FloatBuffer copyBuffer(float[] data){
        FloatBuffer buffer = Buffers.newDirectFloatBuffer(data);
        return buffer;
    }
    public static IntBuffer copyBuffer(int[] data){
        IntBuffer buffer = Buffers.newDirectIntBuffer(data);
        return buffer;
    }

    public static ShortBuffer copyBuffer(short[] data){
        ShortBuffer buffer = Buffers.newDirectShortBuffer(data);
        return buffer;
    }
}
