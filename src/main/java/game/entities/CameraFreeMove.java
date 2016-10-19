package game.entities;

import game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Matthew on 19/10/2016.
 * free move camera for testing
 */
public class CameraFreeMove extends Camera implements KeyListener {
    private float[] position;
    private float pitch;
    private float yaw;


    public CameraFreeMove(){
        position = new float[3];
    }

    //0 up
    //1 down
    //2 left
    //3 right
    //4 space up
    //5 ctrl down
    private static boolean[] CONTROL = new boolean[6];

    public void move(){
        //System.out.println(Game.getGame().getAltitude(position[0],position[2]));
        //position[1] = Game.getGame().getAltitude(position[0],position[2]);
        if(CONTROL[0]){
            position[2] -= 0.1f;
            CONTROL[0] = false;
        }else if(CONTROL[1]){
            position[2] += 0.1f;
            CONTROL[1] = false;
        }

        if(CONTROL[2]){
            position[0] -= 0.1f;

            CONTROL[2] = false;
        }else if(CONTROL[3]){
            position[0] += 0.1f;

            CONTROL[3] = false;
        }

        if(CONTROL[4]){
            position[1] += 0.1f;
            CONTROL[4] = false;
        }else if(CONTROL[5]){
            position[1] -= 0.1f;
            CONTROL[5] = false;
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
            case KeyEvent.VK_SPACE:
                CONTROL[4] = true;
                break;
            case KeyEvent.VK_CONTROL:
                CONTROL[5] = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
