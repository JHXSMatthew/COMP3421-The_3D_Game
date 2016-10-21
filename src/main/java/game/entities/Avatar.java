package game.entities;

import game.Game;
import game.models.ITexturable;
import game.utils.ArrayUtils;
import game.utils.MathUtils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 * Created by Matthew on 20/10/2016.
 */
public class Avatar extends Entity implements KeyListener {

    private static float MAX_SPEED = 2;
    private static int MAX_TURN_DEGREE = 60;

    private float speed = 0;
    private float turning = 0;

    private Light torch;
    private boolean torchOn = false;

    public Avatar(ITexturable model,Light torch) {
        super(model);
        this.torch = torch;
    }

    public Avatar(ITexturable model, float[] position, float[] rotation, float[] scale) {
        super(model, position, rotation, scale);
    }

    public void updateLocation(float time) {
        rotate(ArrayUtils.toArray(0, turning * time, 0));
        float distance = time * speed;
        float x = (float) (distance * Math.sin(Math.toRadians(getRotation()[1])));
        float z = (float) (distance * Math.cos(Math.toRadians(getRotation()[1])));
        move(ArrayUtils.toArray(x, 0, z));
        float[] position = getPosition();
        position[1] = Game.getGame().getAltitude(position[0], position[2]);
        setPosition(position);
        if(torchOn){
            float[] lightByYaw = ArrayUtils.toArray(((float)Math.cos(Math.toRadians(getRotation()[1]))),
                    (float)Math.abs(Math.sin(Math.toRadians(getRotation()[1]))),
                    -(float)Math.cos(Math.toRadians(getRotation()[1])));
            lightByYaw = MathUtils.normalise(lightByYaw);
            torch.setDirection(lightByYaw);
            System.out.println(getRotation()[1]);
            System.out.println(Arrays.toString(lightByYaw));
        }else{
            torch.setDirection(ArrayUtils.toArray(0,0,0));
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.speed = MAX_SPEED;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.speed = -MAX_SPEED;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.turning = MAX_TURN_DEGREE;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.turning = -MAX_TURN_DEGREE;
        }
        if(e.getKeyCode() == KeyEvent.VK_T){
            torchOn = !torchOn;
            System.err.println("torch:" + torchOn);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.speed = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.speed = -0;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.turning = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.turning = -0;
        }
    }
}
