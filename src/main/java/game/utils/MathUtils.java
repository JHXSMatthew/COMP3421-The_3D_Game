package game.utils;

import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.math.Quaternion;
import game.DataBase;
import game.entities.Camera;

/**
 * Created by Matthew on 23/09/2016.
 *
 */
public class MathUtils {
    /*
     * Some maths utilities functions
     * copy from lecture code,
     * ------------------------------------------------copy start --------------------------------------------
     */

    public static float  getMagnitude(float [] n){
        float mag = n[0]*n[0] + n[1]*n[1] + n[2]*n[2];
        mag = (float)Math.sqrt(mag);
        return mag;
    }

    public static float [] normalise(float [] n){
        float  mag = getMagnitude(n);
        float norm[] = {n[0]/mag,n[1]/mag,n[2]/mag};
        return norm;
    }

    public static float [] cross(float u [], float v[]){
        float crossProduct[] = new float[3];
        crossProduct[0] = u[1]*v[2] - u[2]*v[1];
        crossProduct[1] = u[2]*v[0] - u[0]*v[2];
        crossProduct[2] = u[0]*v[1] - u[1]*v[0];
        return crossProduct;
    }

    public static float [] getNormal(float[] p0, float[] p1, float[] p2){
        float u[] = {p1[0] - p0[0], p1[1] - p0[1], p1[2] - p0[2]};
        float v[] = {p2[0] - p0[0], p2[1] - p0[1], p2[2] - p0[2]};

        return cross(u,v);
    }
   // ------------------------------------------------copy end --------------------------------------------


    /**
     * the method to calculate normal of terrain
     * calculate light from four points near the current point
     * @return a normal for terrain
     */
    public static float[] finiteDifference(float[][] altitudes, int x , int z){
        float a = 0,b = 0,c = 0,d = 0;
        if(x != 0){
            a = altitudes[x-1][z];
        }
        if(x +1 < altitudes.length) {
            b = altitudes[x + 1][z];
        }
        if(z != 0){
            c = altitudes[x][z-1];
        }
        if(z + 1 < altitudes.length) {
            d = altitudes[x][z + 1];
        }

        float[] normal = {
                a-b,
                2f,
                c-d,
        };

        normal = normalise(normal);
        return normal;

    }

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

    public static Matrix4 createViewMatrix(Camera camera){
        Matrix4 matrix4 = new Matrix4();
        matrix4.loadIdentity();
        matrix4.rotate((float)Math.toRadians(camera.getYaw()),0,1,0);
        matrix4.translate(-camera.getPosition()[0],-camera.getPosition()[1],-camera.getPosition()[2]);

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
