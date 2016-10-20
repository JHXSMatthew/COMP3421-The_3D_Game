package game.models;

import com.jogamp.opengl.GL2;
import game.render.Loader;
import game.textures.ModelTexture;
import game.utils.OBJUtils;

/**
 * Created by Matthew on 21/10/2016.
 */
public enum OBJTypes implements ITexturable {

    ObjTree("tree.obj","tree.png");

    private TexturedModel model;
    private String name;
    private String texture;

    OBJTypes(String name, String textrue){
        this.name = name;
        this.texture = textrue;
    }

    public void load(GL2 gl, Loader loader){
        model = new TexturedModel(OBJUtils.loadRawModel(gl,name,loader),new ModelTexture(loader.loadTexture(gl,texture)));
    }

    public TexturedModel getModel(){
        return model;
    }

    @Override
    public  TexturedModel getTextureModel(){
        return model;
    }
}
