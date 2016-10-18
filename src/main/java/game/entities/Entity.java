package game.entities;

import game.models.RawModel;
import game.models.Renderable;
import game.models.TexturedModel;
import game.utils.ArrayUtils;
import game.utils.MathUtils;

/**
 * Created by Matthew on 18/10/2016.
 */
public class Entity implements Renderable{

    private TexturedModel model;
    private float[] position;
    private float[] rotation;
    private float[] scale;

    public Entity(TexturedModel model, float[] position , float[] rotation, float[] scale){
        this.model = model;

        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void move(float[] move){
        position = MathUtils.add(move,position);
    }

    public void rotate(float[] move){
        rotation = MathUtils.add(move,rotation);
    }



    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public float[] getRotation() {
        return rotation;
    }

    public void setRotation(float[] rotation) {
        this.rotation = rotation;
    }

    public float[] getScale() {
        return scale;
    }

    public void setScale(float[] scale) {
        this.scale = scale;
    }





    @Override
    public RawModel getRawModel() {
        return model.getRawModel();
    }
}
