package game.models;

import com.jogamp.opengl.GL2;
import game.render.Loader;
import game.textures.ModelTexture;
import game.utils.MathUtils;

import java.util.Arrays;

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
                0,attributes.length-1,
                attributes.length-1,attributes.length-1,
                attributes.length-1,0
        };


        int length = attributes.length;
        int count = attributes.length * attributes.length;
        float[] vertices = new float[3 * count];
        float[] normals = new float[3*count];

        // logic
        // 3 points per triangle, two triangle per vertex, but 1 triangle for top , bottom, left ,right bounds
        // so (hight - 1 ) * (width -1) *2 * 3 indeices needed.
        int[] indices = new int[6*(attributes.length - 1) *(attributes.length -1)];

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

        System.out.println("vertex " + Arrays.toString(vertices));

        System.out.println("indicies " + Arrays.toString(indices));

        System.out.println("normals " + Arrays.toString(normals));

        ModelTexture texture =  new ModelTexture(loader.loadTexture(gl,"grass.jpg"));
        RawModel rawModel = loader.loadToVAO(gl,vertices,indices,textureCoords,normals);
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
