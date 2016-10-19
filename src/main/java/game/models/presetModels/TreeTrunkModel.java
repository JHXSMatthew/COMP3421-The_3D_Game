package game.models.presetModels;


import com.jogamp.opengl.GL2;

/**
 * Created by Matthew on 19/10/2016.
 * tree is a sphere and a cylidrical trunk
 * I prefer to introduce OBJ format here but it's very interesting to mutually draw it using loops.
 *
 */
public class TreeTrunkModel extends PresetModel{


    public TreeTrunkModel(String textureFileName) {
        super(textureFileName);
    }

    @Override
    protected float[] getVertex(GL2 gl) {
        return new float[0];
    }

    @Override
    protected float[] getNormals(GL2 gl) {
        return new float[0];
    }

    @Override
    protected float[] getTexturedCords(GL2 gl) {
        return new float[0];
    }

    @Override
    protected int[] getIndices(GL2 gl) {
        return new int[0];
    }

    @Override
    protected void onSetup(GL2 gl) {

    }

    @Override
    protected void setInstance() {

    }
}
