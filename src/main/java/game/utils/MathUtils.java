package game.utils;

import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.math.Quaternion;

/**
 * Created by Matthew on 23/09/2016.
 *
 */
public class MathUtils {
    /*
     * Some maths utility functions
     * copy from lecture code,
     * ------------------------------------------------copy start --------------------------------------------
     */

    public static double  getMagnitude(double [] n){
        double mag = n[0]*n[0] + n[1]*n[1] + n[2]*n[2];
        mag = Math.sqrt(mag);
        return mag;
    }

    public static double [] normalise(double [] n){
        double  mag = getMagnitude(n);
        double norm[] = {n[0]/mag,n[1]/mag,n[2]/mag};
        return norm;
    }

    public static double [] cross(double u [], double v[]){
        double crossProduct[] = new double[3];
        crossProduct[0] = u[1]*v[2] - u[2]*v[1];
        crossProduct[1] = u[2]*v[0] - u[0]*v[2];
        crossProduct[2] = u[0]*v[1] - u[1]*v[0];
        return crossProduct;
    }

    public static double [] getNormal(double[] p0, double[] p1, double[] p2){
        double u[] = {p1[0] - p0[0], p1[1] - p0[1], p1[2] - p0[2]};
        double v[] = {p2[0] - p0[0], p2[1] - p0[1], p2[2] - p0[2]};

        return cross(u,v);
    }
   // ------------------------------------------------copy end --------------------------------------------


    public static double[] createDouble(double x , double y , double z , boolean point){
        double last = 0;
        if(point){
            last = 1;
        }
        double[] d = new double[4];
        d[0] = x;
        d[1] = y;
        d[2] = z;
        d[3] =last;
        return d;
    }

    public static int sizeOf(Class<? extends Number> clazz){
        int size = 0;
        try {
            size = clazz.getDeclaredField("SIZE").getInt(null)/ Byte.SIZE;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return size;
    }

    public static Matrix4 createTransforMatrix(float[] translation, float[] rotate, float[] scale){
        Matrix4 matrix4 = new Matrix4();
        matrix4.loadIdentity();
        matrix4.translate(translation[0],translation[1],translation[2]);
        matrix4.rotate((float)Math.toRadians(rotate[0]),1,0,0);
        matrix4.rotate((float)Math.toRadians(rotate[1]),0,1,0);
        matrix4.rotate((float)Math.toRadians(rotate[2]),0,0,1);
        matrix4.scale(scale[0],scale[1],scale[2]);

        return matrix4;

    }

    public static float[] add(float[]in, float[] in2){
        float[] f = new float[3];
        for(int i = 0 ; i < 3 ; i ++){
            f[i] = in[i] + in2[i];
        }
        return f;
    }

}
