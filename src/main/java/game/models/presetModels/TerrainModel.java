package game.models.presetModels;

import com.jogamp.opengl.GL2;
import game.utils.MathUtils;

/**
 * Created by Matthew on 19/10/2016.
 */
public class TerrainModel extends PresetModel  {

    private static TerrainModel instance;

    private float[][] attributes= null;


    public TerrainModel(float[][] attributes) {
        super("grass.jpg");
        this.attributes = attributes;

        int length = attributes.length;
        int count = attributes.length * attributes.length;
        vertices = new float[3 * count];
        normals = new float[3*count];
        textureCoords = new float[2*count];

        // logic
        // 3 points per triangle, two triangle per vertex, but 1 triangle for top , bottom, left ,right bounds
        // so (hight - 1 ) * (width -1) *2 * 3 indeices needed.
        indices = new int[6*(attributes.length - 1) *(attributes.length -1)];

        int curr = 0;
        int offSet = 0;

        for(int z = 0 ; z < length ; z ++){
            for(int x = 0 ; x < length ; x ++){
                System.arraycopy(MathUtils.finiteDifference(attributes,x,z),0,normals,offSet,3);
                vertices[offSet++] = x;
                vertices[offSet++] = attributes[x][z];
                vertices[offSet++] = z;

                /**
                 *  logic here
                 *  every terrian can be divided as 2*2 from any point of this array (x,x+1,z,z+1)
                 *  each 2*2 can be divided to two triangles (up, down)
                 *  basically loop through all possible 2*2 squares and calculate trangle indicies
                 *  the trangle coordinates can be known by calculating the squares position.
                 *  skip bounds, if the point is on the bound, -1 trangle, so basically -2 with two bounds
                 * a:(z*attributes.length)  + x    b:(z*attributes.length) +1
                             *    -----
                             *   |    |
                             *   |____|
                 *d:(z+1)*attributes.length + x    c:(z+1)*attribute.length + x+1
                 */
                if(x != attributes.length - 1 && z != attributes.length -1) {
                    int a = (z * attributes.length) + x;
                    int b = a + 1;
                    int d = (z+1)*attributes.length + x;;
                    int c = d+1;
                    //adb
                    indices[curr++] = a;
                    indices[curr++] = b;
                    indices[curr++] = d;
                    //bcd
                    indices[curr++] = b;
                    indices[curr++] = c;
                    indices[curr++] = d;


                }

            }
        }

        offSet = 0;
        for(int z = 0 ; z < length ; z ++){
            for(int x = 0 ; x < length ; x ++){
                textureCoords[offSet++] = x;
                textureCoords[offSet++] = z;
            }
        }
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
