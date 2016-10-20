package game.entities;

import game.models.ITexturable;
import game.models.RawModel;
import game.models.IRenderable;
import game.models.TexturedModel;
import game.utils.ArrayUtils;
import game.utils.MathUtils;


/**
 * Created by Matthew on 18/10/2016.
 */
public class Entity implements IRenderable {

    private TexturedModel model;
    private float[] position;
    private float[] rotation;
    private float[] scale;

    private boolean show = true;


    public Entity(ITexturable model){
        this.model = model.getTextureModel();
        this.position = ArrayUtils.toArray(0,0,0);
        this.rotation = ArrayUtils.toArray(0,0,0);
        this.scale =  ArrayUtils.toArray(1,1,1);
    }

    public Entity(ITexturable model, float[] position , float[] rotation, float[] scale){
        this.model = model.getTextureModel();

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


    public boolean shouldShow(){
        return show;
    }

    public void hide(){
        show = false;
    }

    public void show(){
        show = true;
    }




    @Override
    public RawModel getRawModel() {
        return model.getRawModel();
    }
}
