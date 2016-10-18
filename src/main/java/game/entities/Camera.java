package game.entities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Matthew on 19/10/2016.
 */
public class Camera implements KeyListener {
    private float[] position;
    private float pitch;
    private float yaw;


    public Camera(){
        position = new float[3];
    }

    //0 up
    //1 down
    //2 left
    //3 right
    private static boolean[] CONTROL = new boolean[4];

    public void move(){
        if(CONTROL[0]){
            position[2] -= 0.02f;
            CONTROL[0] = false;
        }else if(CONTROL[1]){
            position[2] += 0.02f;
            CONTROL[1] = false;
        }

        if(CONTROL[2]){
            yaw -= 1;
            CONTROL[2] = false;
        }else if(CONTROL[3]){
            yaw += 1;
            CONTROL[3] = false;
        }
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                CONTROL[2] = true;
                break;
            case KeyEvent.VK_RIGHT:
                CONTROL[3] = true;
                break;
            case KeyEvent.VK_UP:
                CONTROL[0]  = true;
                break;
            case KeyEvent.VK_DOWN:
                CONTROL[1] = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
