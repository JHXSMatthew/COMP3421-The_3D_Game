package game.entities;

/**
 * Created by Matthew on 19/10/2016.
 */
public class Light {

    private float[] color;
    private float[] direction;
    //extra ambient
    private float ambient = 0;

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public Light(float[] toVector,float[] color) {
        this.color = color;
        this.direction = toVector;
    }

    public float[] getDirection() {
        return direction;
    }

    public void setDirection(float[] direction) {
        this.direction = direction;
    }
}
