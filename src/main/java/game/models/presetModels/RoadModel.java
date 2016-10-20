package game.models.presetModels;

import com.jogamp.opengl.GL2;
import game.Config;
import game.Game;
import game.entities.RoadPrototype;

import java.util.Arrays;


/**
 * Created by Matthew on 20/10/2016.
 */
public class RoadModel extends PresetModel {

    private static RoadModel instance;
    double move;
    double[] max;
    double[] min;
    private RoadPrototype prototype;
    private int count = 10;

    public RoadModel(RoadPrototype prototype) {
        super("road.jpg");
        this.count = Config.roadComplexity;

        this.prototype = prototype;

        vertices = new float[count * 3 * 3];
        indices = new int[count * 3];
        normals = new float[count * 3 * 3];
        textureCoords = new float[count * 3 * 2];

        int pointer = 0;
        move = Math.sqrt(prototype.width() * prototype.width() / 2) / 2;
        max = prototype.point(prototype.size() - 1);
        min = prototype.point(0);

        float step = prototype.size() / ((float) count);
        for (float t = 0; t < prototype.size(); t += step) {
            double[] base = prototype.point(t);
            base[0] = base[0] - min[0];
            base[1] = base[1] - min[1];
            System.out.println(Arrays.toString(base));
         //   addVertex(base, pointer, move);


           // pointer++;
            addVertex(base, pointer, 0);


            pointer++;
          //  addVertex(base, pointer, -move);

        }

        float min = 0;
        for(int i = 0 ; i < textureCoords.length ; i ++){
            if(textureCoords[i] < min){
                textureCoords[i] = min;
            }
        }
        min = -min;
        for(int i = 0 ; i < textureCoords.length ; i ++){
            textureCoords[i] += min;
        }
        prototype.setModel(this);

    }

    private void addVertex(double base[], int pointer, double diff) {
        vertices[3 * pointer] = (float) (base[0] + diff);
        vertices[3 * pointer + 1] = Game.getGame().getAltitude((float) min[0], (float) min[1]);
        vertices[3 * pointer + 2] = (float) (base[1] + diff);
        normals[3 * pointer] = 0;
        normals[3 * pointer + 1] = 1;
        normals[3 * pointer + 2] = 0;

        indices[pointer] = pointer;
        /*textureCoords[2 * pointer] = (float) (vertices[pointer] / max[0]);
        textureCoords[2 * pointer + 1] = (float) (vertices[pointer + 2] / max[1]);*/
        textureCoords[2 * pointer] = (float) (vertices[pointer] / max[0]);
        textureCoords[2 * pointer + 1] = (float) (vertices[pointer + 2] / max[1]);
    }

    @Override
    protected float[] getVertex(GL2 gl) {
        return vertices;
    }

    @Override
    protected float[] getNormals(GL2 gl) {
        return normals;
    }

    @Override
    protected float[] getTexturedCords(GL2 gl) {
        return textureCoords;
    }

    @Override
    protected int[] getIndices(GL2 gl) {
        return indices;
    }

    @Override
    protected void onSetup(GL2 gl) {
        getRawModel().setMeshMode(GL2.GL_LINE);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }
}
