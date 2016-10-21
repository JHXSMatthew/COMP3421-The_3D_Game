package game.utils;

import java.util.List;

/**
 * Created by Matthew on 30/09/2016.
 */
public class ArrayUtils {

    public static int[] getIntArrayFromList(List<Integer> integerList) {
        int[] array = new int[integerList.size()];
        for (int i = 0; i < integerList.size(); i++) {
            array[i] = integerList.get(i);
        }
        return array;
    }

    public static float[] getFloatArrayFromList(List<Float> integerList) {
        float[] array = new float[integerList.size()];
        for (int i = 0; i < integerList.size(); i++) {
            array[i] = integerList.get(i);
        }
        return array;
    }

    public static float[] toArray(Float[] array) {
        float[] r = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            r[i] = array[i];
        }
        return r;
    }

    public static Float[] toArrayC_L(float[] array) {
        Float[] r = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            r[i] = array[i];
        }
        return r;
    }


    public static float[] toArray(float... f) {
        return f;
    }


}
