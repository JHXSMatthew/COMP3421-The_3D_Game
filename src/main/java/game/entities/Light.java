package game.entities;

import game.Ticker;
import game.utils.MathUtils;
import org.omg.CORBA.TIMEOUT;

/**
 * Created by Matthew on 19/10/2016.
 */
public class Light {

    private float[] color;
    private float[] direction;

    private static float ambient = 0.01f;


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
            ambient = (float)Math.cos((float)baisedTime/ Ticker.A_DAY *  Math.PI/2f) * 0.02f;
           // System.out.println((float)Math.cos((float)baisedTime/ Ticker.A_DAY *  Math.PI/2f)  + " , a:" +ambient);
        }

    }

    public static float getAmbient(){
        return ambient;
    }
}
