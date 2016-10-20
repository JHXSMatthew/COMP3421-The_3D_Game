package game.entities;

import game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Matthew on 19/10/2016.
 */
public class Camera implements KeyListener {
    private float[] position;
    private float pitch = -45;
    private float yaw;



    private Avatar avatar;
    private float zoom = 3;
    private float angle = 45;

    public Camera(){
        position = new float[3];
    }

    //0 up
    //1 down
    //2 left
    //3 right
    private static boolean[] CONTROL = new boolean[4];




    public void move(){


        float horizontal = (float) (zoom * Math.cos(pitch));
        float vertical = (float) (zoom * Math.sin(pitch));
        position[0] = avatar.getPosition()[0] - (float)(horizontal * Math.sin((Math.toRadians(avatar.getRotation()[1] + angle))));
        position[1] = avatar.getPosition()[1] + vertical;
        position[2] = avatar.getPosition()[2] - (float)(horizontal * Math.cos((Math.toRadians(avatar.getRotation()[1] + angle))));
        //yaw = 180 - (avatar.getRotation()[1] + angle);




        /*
        position[1] = Game.getGame().getAltitude(position[0],position[2]);

        double distance = 0.02;
        if(CONTROL[0]){
            position[0] += (float)(Math.sin(Math.toRadians(yaw)) * distance);
            position[2] += (float)(Math.cos(Math.toRadians(yaw)) * distance);
            CONTROL[0] = false;
        }else if(CONTROL[1]){
            position[0] += (float)(Math.sin(Math.toRadians(yaw)) * -distance);
            position[2] += (float)(Math.cos(Math.toRadians(yaw)) * -distance);
            CONTROL[1] = false;
        }

        if(CONTROL[2]){
            yaw -= 1;
            CONTROL[2] = false;
        }else if(CONTROL[3]){
            yaw += 1;
            CONTROL[3] = false;
        }
        */

    }


    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
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
        return yaw - 180;
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
