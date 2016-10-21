package game.entities;

import game.Config;
import game.Game;
import game.Ticker;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Matthew on 19/10/2016.
 */
public class Camera implements KeyListener {
    //0 up
    //1 down
    //2 left
    //3 right
    private static boolean[] CONTROL = new boolean[4];
    private float[] position;
    private float pitch = 15;
    private float yaw;
    private Avatar avatar;
    private float zoom = 7;
    private float angle = 0;
    private boolean thirdPerson = true;

    public Camera() {
        position = new float[3];
    }

    public void move() {
        if (thirdPerson) {
            avatar.show();
            float horizontal = (float) (zoom * Math.cos(Math.toRadians(pitch)));
            float vertical = (float) (zoom * Math.sin(Math.toRadians(pitch)));
            position[0] = avatar.getPosition()[0] - (float) (horizontal * Math.sin((Math.toRadians(avatar.getRotation()[1] + angle))));
            position[1] = avatar.getPosition()[1] + vertical;
            position[2] = avatar.getPosition()[2] - (float) (horizontal * Math.cos((Math.toRadians(avatar.getRotation()[1] + angle))));
            yaw = 180 - (avatar.getRotation()[1] + angle);
        } else {
            avatar.hide();
            for (int i = 0; i < 3; i++) {
                position[i] = avatar.getPosition()[i];
            }
            position[1] += 0.1;
            yaw = 180 - avatar.getRotation()[1];
        }




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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                pitch += 0.5;
                break;
            case KeyEvent.VK_CONTROL:
                pitch -= 0.5;
                break;
            case KeyEvent.VK_Z:
                zoom++;
                break;
            case KeyEvent.VK_X:
                zoom--;
                break;
            case KeyEvent.VK_P:
                this.thirdPerson = !this.thirdPerson;
                break;
            case KeyEvent.VK_N:
                Game.getGame().setTime(Ticker.NIGHT + Ticker.interval);
                System.err.println("set to night!");
                break;
            case KeyEvent.VK_M:
                Game.getGame().setTime(Ticker.NOON + 1);
                System.err.println("set to Noon!");
                break;
            case KeyEvent.VK_COMMA:
                Config.timePass = !Config.timePass;
                System.err.println("time passing:" + Config.timePass);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
