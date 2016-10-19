package game.models.presetModels;

import com.jogamp.opengl.GL2;
import game.models.IRenderable;
import game.models.ITexturable;
import game.models.RawModel;
import game.models.TexturedModel;
import game.render.Loader;
import game.textures.ModelTexture;

/**
 * Created by Matthew on 19/10/2016.
 */
public abstract class PresetModel implements IRenderable,ITexturable {

    private TexturedModel model;
    private String textureFileName;


    public PresetModel(String textureFileName){
        this.textureFileName = textureFileName;
    }

    public void setUp(GL2 gl,Loader loader){
        ModelTexture texture =  new ModelTexture(loader.loadTexture(gl,textureFileName));
        RawModel rawModel = loader.loadToVAO(gl,getVertex(gl),getIndices(gl), getTexturedCords(gl),getNormals(gl));
        this.model = new TexturedModel(rawModel,texture);
        setInstance();
        onSetup(gl);

    }

    public void setSpecularLight(float reflectivity,int dampper){
        model.getTexture().setReflectivity(reflectivity);
        model.getTexture().setShineDamper(dampper);

    }

    protected abstract float[] getVertex(GL2 gl);
    protected abstract float[] getNormals(GL2 gl);
    protected abstract float[] getTexturedCords(GL2 gl);
    protected abstract int[] getIndices(GL2 gl);
    protected abstract void onSetup(GL2 gl);

    protected abstract void setInstance();

    @Override
    public RawModel getRawModel() {
        return model.getRawModel();
    }

    @Override
    public TexturedModel getTextureModel() {
        return model;
    }

}
