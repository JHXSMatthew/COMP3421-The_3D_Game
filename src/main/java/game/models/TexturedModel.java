package game.models;

import game.textures.ModelTexture;

/**
 * Created by Matthew on 18/10/2016.
 */
public class TexturedModel implements IRenderable,ITexturable {

    private RawModel model;
    private ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture){
        this.model = model;
        this.texture = texture;
    }

    public RawModel getModel(){
        return model;
    }

    public ModelTexture getTexture(){
        return texture;
    }

    @Override
    public RawModel getRawModel() {
        return model;
    }

    @Override
    public TexturedModel getTextureModel() {
        return this;
    }
}
