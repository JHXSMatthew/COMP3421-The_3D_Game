package game.entities;

import game.Config;
import game.Ticker;
import game.utils.ArrayUtils;
import game.utils.MathUtils;
import org.omg.CORBA.TIMEOUT;

import java.util.Arrays;

/**
 * Created by Matthew on 19/10/2016.
 */
public class Light {

    private float[] color;
    private float[] direction;

    private static float ambient = 0.02f;

    private float[] copyDirection = null;


    public Light(float[] toVector, float[] color) {
        this.color = color;
        this.direction = toVector;
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public float[] getDirection() {
        return direction;
    }

    public void setDirection(float[] direction) {
        this.direction = direction;
    }

    public static void setAmbient(int time){
        if(time > Ticker.MORNING && time < Ticker.NIGHT){
            int baisedTime = time - Ticker.NOON;
            ambient = (float)Math.cos((float)baisedTime/ Ticker.A_DAY *  Math.PI/2f) * 0.04f;
           // System.out.println((float)Math.cos((float)baisedTime/ Ticker.A_DAY *  Math.PI/2f)  + " , a:" +ambient);
        }else{
            int baisedTime = time - Ticker.NOON;
            ambient = (float)Math.cos((float)baisedTime/ Ticker.A_DAY *  Math.PI/2f) * -0.1f;
           // System.out.println((float)Math.cos((float)baisedTime/ Ticker.A_DAY *  Math.PI/2f)  + " , a:" +ambient);
        }

    }

    public static float getAmbient(){
        return ambient;
    }

    public void setColor(int time){

    }
    //move light by time light with constant position Z
    public void setPosition(int time){
        if(copyDirection == null)
            copyDirection = direction;

        if(Config.timePass) {
            if(time > Ticker.MORNING && time < Ticker.NIGHT) {

                float[] noon = ArrayUtils.toArray(0, 1, 0);
                double angle = Math.toRadians(180 - 150 * ((float) (time-Ticker.MORNING) / (Ticker.NIGHT - Ticker.MORNING)));


                noon[1] = (float) Math.sin(angle);
                noon[0] = (float) Math.cos(angle);
                noon[2] = (float) Math.cos(angle);
               // System.err.println("D-" + Math.toDegrees(angle) + " " + Arrays.toString(noon));

                this.direction = noon;
           }else{
                float[] night = ArrayUtils.toArray(0, 1, 0);

                double angle = 0;
                if(time > Ticker.NIGHT){
                    angle = Math.toRadians(150 - 120 * ( ((float)time - Ticker.NIGHT) / (Ticker.NIGHT - Ticker.MORNING)));
                }else {
                    angle = Math.toRadians(150 - 120 * ( ((float)time + (Ticker.A_DAY - Ticker.NIGHT)) / (Ticker.NIGHT - Ticker.MORNING)));
                }


                night[1] = (float) Math.sin(angle);
                night[0] = (float) Math.cos(angle);
                night[2] = (float) Math.cos(angle);
               // System.err.println("N- " + Math.toDegrees(angle) + " " + Arrays.toString(night));
                this.direction = night;
            }

        }else{
            this.direction =copyDirection;
        }
    }
}
