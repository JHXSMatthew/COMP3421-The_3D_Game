package game.entities;

import game.models.TexturedModel;

/**
 * Created by Matthew on 19/10/2016.
 */
public class Light extends Entity{

    private float[] color;

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public Light(float[] position,float[] color) {
        super(null, position, null, null);
        this.color = color;
    }
}
