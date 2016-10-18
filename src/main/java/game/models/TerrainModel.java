package game.models;

import com.jogamp.opengl.GL2;
import game.render.Loader;
import game.textures.ModelTexture;

/**
 * Created by Matthew on 19/10/2016.
 */
public class TerrainModel implements IRenderable,ITexturable {

    private TexturedModel model;
    private static TerrainModel terrain;

    public TerrainModel(GL2 gl, float[][] attributes, Loader loader) {
        //fixed texture stuff
        float[] textureCoords = {
                0,0,
                0,attributes.length,
                attributes.length,attributes.length,
                attributes.length,0
        };



        int count = attributes.length * attributes.length;
        float[] vertices = new float[3 * count];
        // logic
        // 3 points per triangle, two triangle per vertex, but 1 triangle for top , bottom, left ,right bounds
        // so (hight - 1 ) * (width -1) *2 * 3 indeices needed.
        int[] indices = new int[6*(attributes.length - 1) *(attributes.length -1)];

        int curr = 0;
        for(int z = 0 ; z < attributes.length ; z ++){
            for(int x = 0 ; x < attributes.length ; x ++){
                int offSet = x*z;
                vertices[offSet] = x;
                vertices[offSet + 1] = z;
                vertices[offSet + 2] = attributes[x][z];

                /**
                 *  logic here
                 *  every terrian can be divided as 2*2 from any point of this array (x,x+1,z,z+1)
                 *  each 2*2 can be divided to two triangles (up, down)
                 *  basically loop through all possible 2*2 squares and calculate trangle indicies
                 *  the trangle coordinates can be known by calculating the squares position.
                 *  skip bounds, if the point is on the bound, -1 trangle, so basically -2 with two bounds
                 * a:(z*attributes.length)     b:(z*attributes.length) +1
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
                    indices[curr++] = d;
                    indices[curr++] = b;
                    //bdc
                    indices[curr++] = b;
                    indices[curr++] = d;
                    indices[curr++] = c;
                }
            }
        }



        ModelTexture texture =  new ModelTexture(loader.loadTexture(gl,"grass.jpg"));
        RawModel rawModel = loader.loadToVAO(gl,vertices,indices,textureCoords);
        this.model = new TexturedModel(rawModel,texture);
        terrain = this;
    }

    public static TerrainModel getModel(){
        return terrain;
    }

    @Override
    public RawModel getRawModel() {
        return model.getRawModel();
    }

    @Override
    public TexturedModel getTextureModel() {
        return model;
    }
}
