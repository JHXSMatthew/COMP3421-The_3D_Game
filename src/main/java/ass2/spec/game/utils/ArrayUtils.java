package ass2.spec.game.utils;

import java.util.List;

/**
 * Created by Matthew on 30/09/2016.
 */
public class ArrayUtils {

    public static int[] getIntArrayFromList(List<Integer> integerList){
        int[] array = new int[integerList.size()];
        for(int i = 0 ; i < integerList.size(); i++){
            array[i] = integerList.get(i);
        }
        return array;
    }
}
