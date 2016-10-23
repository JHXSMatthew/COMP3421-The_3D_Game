package game.models.presetModels;

import com.jogamp.opengl.GL2;
import game.Config;
import game.Game;
import game.entities.RoadPrototype;
import game.utils.ArrayUtils;
import game.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;


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
    private float generalHeight = 0;

    public RoadModel(RoadPrototype prototype) {
        super("cube.png");
        this.count = Config.roadComplexity;

        this.prototype = prototype;

        vertices = new float[count * 3 * 3];
        indices = new int[count * 3];
        normals = new float[count * 3 * 3];
        textureCoords = new float[count * 3 * 2];

        List<Float> vertices = new ArrayList<>();
        List<Float> normals = new ArrayList<>();
        List<Float> textureCoords = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float step = prototype.size() / ((float) count);

        move = prototype.width()/2;
        max = prototype.getMax();
        min = prototype.getMin();

        generalHeight = Game.getGame().getAltitude((float) max[0], (float) max[1]);

        if (Game.getGame().getAltitude((float) min[0], (float) min[1]) > generalHeight) {
            generalHeight = Game.getGame().getAltitude((float) min[0], (float) min[1]);
        }
        //System.out.println(generalHeight);

        List<Float[]> inter = new ArrayList<>();
        inter.add(ArrayUtils.toArrayC_L(ArrayUtils.toArray(0, (float) move, 0, 1f)));
        inter.add(ArrayUtils.toArrayC_L(ArrayUtils.toArray(0, (float) -move, 0, 1f)));

        for (float t = step; t < prototype.size() - step; t += step) {

            try {
                addPoints(textureCoords,inter, vertices, get3DPoint(prototype.point(t)), get3DPoint(prototype.point(t - step)), get3DPoint(prototype.point(t + step)),step);
            } catch (Exception e) {

            }
        }
        addPoints(textureCoords,inter, vertices, get3DPoint(prototype.point(prototype.size() - 2 * step)), get3DPoint(prototype.point(prototype.size() - step)), get3DPoint(prototype.point(prototype.size() - step)),step);


        this.vertices = ArrayUtils.getFloatArrayFromList(vertices);

        //indices.add(0);
        //indices.add(1);
        //indices.add(2);
        for (int i = 1; i < this.vertices.length / 3 - 1; i++) {
            indices.add(i);
            indices.add(i - 1);
            indices.add(i + 1);
        }

        this.indices = ArrayUtils.getIntArrayFromList(indices);

        List<Float> tNew = new ArrayList<Float>();
        for (Integer i : indices) {
            normals.add(0f);
            normals.add(1f);
            normals.add(0f);
            float bias = 0;
            if(i % 2 != 0){
                bias = (float)(prototype.width()/max[1]);
            }
            float mapX = textureCoords.get(i)  ;//(float) Math.abs((this.vertices[3 * i] - min[0]) / (max[0] - min[0]));
            float mapZ = textureCoords.get(i+1)  ;//(float) Math.abs(((this.vertices[3 * i + 2] - min[1]) / (max[1] - min[1]) * 1.2));
            tNew.add((float)(mapX - min[0])/(float)(max[0] - min[0]));
            tNew.add((float)(mapZ - min[1])/(float)(max[1]-min[1]));
            //textureCoords.add((float)(this.vertices[i] - min[0]));

            //textureCoords.add((float)(this.vertices[i+2] - min[1]));
           // System.out.println(i + " mapping to " +mapX+"," + mapZ);

        }


        this.normals = ArrayUtils.getFloatArrayFromList(normals);
        this.textureCoords = ArrayUtils.getFloatArrayFromList(textureCoords);

        //System.out.println("i:" + indices.size() + " v:" + vertices.size() + " n:" + normals.size() + " t:" + textureCoords.size());


        prototype.setModel(this);

    }


    private float[] get3DPoint(double[] d) {
        float[] r = new float[3];
        r[0] = (float) d[0];
        r[2] = (float) d[1];
        return r;
    }


    private void addPoints(List<Float> textures , List<Float[]> crossSection, List<Float> vertices,
                           float[] pPrev, float[] pCurr, float[] pNext, float t) {

        // compute the Frenet frame as an affine matrix
        float[][] m = new float[4][4];

        // phi = pCurr
        m[0][3] = pCurr[0];
        m[1][3] = pCurr[1];
        m[2][3] = pCurr[2];
        m[3][3] = 1;

        // k = pNext - pPrev (approximates the tangent)

        m[0][2] = pNext[0] - pPrev[0];
        m[1][2] = pNext[1] - pPrev[1];
        m[2][2] = pNext[2] - pPrev[2];
        m[3][2] = 0;


        // normalise k
        double d = Math.sqrt(m[0][2] * m[0][2] + m[1][2] * m[1][2] + m[2][2] * m[2][2]);
        m[0][2] /= d;
        m[1][2] /= d;
        m[2][2] /= d;

        // i = simple perpendicular to k
        m[0][0] = -m[1][2];
        m[1][0] = m[0][2];
        m[2][0] = 0;
        m[3][0] = 0;


        // normalized i
        d = Math.sqrt(m[0][0] * m[0][0] + m[1][0] * m[1][0] + m[2][0] * m[2][0]);
        m[0][0] /= d;
        m[1][0] /= d;
        m[2][0] /= d;

        // j = k x i
        m[0][1] = m[1][2] * m[2][0] - m[2][2] * m[1][0];
        m[1][1] = m[2][2] * m[0][0] - m[0][2] * m[2][0];
        m[2][1] = m[0][2] * m[1][0] - m[1][2] * m[0][0];
        m[3][1] = 0;

        // normalized j
        d = Math.sqrt(m[0][1] * m[0][1] + m[1][1] * m[1][1] + m[2][1] * m[2][1]);
        m[0][1] /= d;
        m[1][1] /= d;
        m[2][1] /= d;
        /*
        System.out.println();
        for(int i = 0 ; i < m.length ; i  ++){
            System.out.println(Arrays.toString(m[i]));
        }*/

        // transform the points
        //float[][] dis = new float[2][];
        for (Float[] cp : crossSection) {
            float[] position = MathUtils.multiply(m, ArrayUtils.toArray(cp));
            float tX = position[0];
            float tY = position[2];


           /* if(dis[0] == null){
                dis[0] = position;
            }else{
                dis[1] = position;
            }*/

            vertices.add(position[0]);
            textures.add(tX);
            if (Config.roadUpHill) {
                float height = Game.getGame().getAltitude(position[0], position[2]);
                if (height < generalHeight) {
                    height = generalHeight;
                }
                vertices.add(height);
            } else {
                vertices.add(generalHeight);

            }
            vertices.add(position[2]);
            textures.add(tY);
            for(int i = 0 ; i < 2 ; i ++){
                if(position[i] > max[i]){
                    max[i] = position[i];
                }
                if(position[i] < min[i]){
                    min[i] = position[i];
                }
            }
        }

       // System.out.println("distance " + ((dis[0][2] - dis[1][2])*(dis[0][2] - dis[1][2]) + (dis[0][0] - dis[1][0])*(dis[0][0] - dis[1][0])));


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
        getRawModel().setMeshMode(GL2.GL_TRIANGLES);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }
}
