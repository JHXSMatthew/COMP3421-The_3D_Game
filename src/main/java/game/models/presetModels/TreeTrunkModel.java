package game.models.presetModels;


import com.jogamp.opengl.GL2;
import game.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 19/10/2016.
 * tree is a sphere and a cylindrical trunk
 * I prefer to introduce OBJ format here but it's very interesting to mutually draw it using loops.
 * but for god sake, it takes years.
 *
 */
public class TreeTrunkModel extends PresetModel{

    private int slices = 32;

    private float[] vertices;
    private float[] textureCoords;
    private float[] normals;
    private int[] indices;


    private static TreeTrunkModel instance;

    public TreeTrunkModel(String textureFileName) {
        super("trunk.jpg");
        List<Float> vertices = new ArrayList<Float>();
        List<Integer> indicies = new ArrayList<Integer>();
        List<Float> textureCoords = new ArrayList<Float>();

        List<Float> normals = new ArrayList<Float>();


        double angleIncrement = (Math.PI * 2.0) / slices;
        double zFront = -1;
        double zBack = -3;


        for(int i=0; i<= slices; i++){
            double angle0 = i*angleIncrement;
            double xPos0 = Math.cos(angle0);
            double yPos0 = Math.sin(angle0);
            double sCoord = 2.0/slices * i; //Or * 2 to repeat label


            normals.add((float)xPos0);
            normals.add((float)yPos0);
            normals.add(0f);

            textureCoords.add((float)sCoord);
            textureCoords.add(1f);
            vertices.add((float)xPos0);
            vertices.add((float)yPos0);
            vertices.add((float)zFront);
            indicies.add(vertices.size() -4);

            textureCoords.add((float)sCoord);
            textureCoords.add(0f);

            vertices.add((float)xPos0);
            vertices.add((float)yPos0);
            vertices.add((float)zBack);
            indicies.add(vertices.size() -4);

        }

        this.vertices = ArrayUtils.getIntArrayFromListFloat(vertices);
        this.normals = ArrayUtils.getIntArrayFromListFloat(normals);
        this.textureCoords = ArrayUtils.getIntArrayFromListFloat(textureCoords);
        this.indices = ArrayUtils.getIntArrayFromList(indicies);


    }


    @Override
    protected float[] getVertex(GL2 gl) {
        float[] returnValue = vertices;
        this.vertices = null;
        return returnValue;
    }

    @Override
    protected float[] getNormals(GL2 gl) {
        float[] returnValue = normals;
        this.normals = null;
        return returnValue;
    }

    @Override
    protected float[] getTexturedCords(GL2 gl) {
        float[] returnValue = textureCoords;
        this.textureCoords = null;
        return returnValue;
    }

    @Override
    protected int[] getIndices(GL2 gl) {
        int[] returnValue = indices;
        this.indices = null;
        return returnValue;
    }

    @Override
    protected void onSetup(GL2 gl) {

    }

    @Override
    protected void setInstance() {
        instance = this;
    }
}
