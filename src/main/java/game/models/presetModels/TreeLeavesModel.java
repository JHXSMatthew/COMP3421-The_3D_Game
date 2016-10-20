package game.models.presetModels;


import com.jogamp.opengl.GL2;
import game.utils.MathUtils;

import java.nio.FloatBuffer;

/**
 * Created by Matthew on 19/10/2016.
 * tree is a sphere and a cylidrical trunk
 * I prefer to introduce OBJ format here but it's very interesting to mutually draw it using loops.
 * <p>
 * pretty much code from lecture.
 */
public class TreeLeavesModel extends PresetModel {

    private static TreeLeavesModel instance;
    private int maxStacks = 20;
    private int maxSlices = 30;
    private int maxVertices = maxStacks * (maxSlices + 1) * 2;


    public TreeLeavesModel() {
        super("leaves.jpg");
        FloatBuffer verticesBuffer = FloatBuffer.allocate(maxVertices * 3);
        FloatBuffer normalsBuffer = FloatBuffer.allocate(maxVertices * 3);
        textureCoords = new float[maxVertices * 2];

        float deltaT;
        deltaT = (float) 0.5 / maxStacks;
        int ang;
        int delang = 360 / maxSlices;
        double x1, x2, z1, z2, y1, y2;
        float radius = 0.5f;
        int textureCos = 0;
        for (int i = 0; i < maxStacks; i++) {
            double t = -0.25 + i * deltaT;
            for (int j = 0; j <= maxSlices; j++) {
                ang = j * delang;
                x1 = (float) (radius * r(t) * Math.cos((double) ang * 2.0 * Math.PI / 360.0));
                x2 = (float) (radius * r(t + deltaT) * Math.cos((double) ang * 2.0 * Math.PI / 360.0));
                y1 = (float) (radius * getY(t));

                z1 = (radius * r(t) * Math.sin((double) ang * 2.0 * Math.PI / 360.0));
                z2 = (radius * r(t + deltaT) * Math.sin((double) ang * 2.0 * Math.PI / 360.0));
                y2 = (radius * getY(t + deltaT));

                float normal[] = {(float) x1, (float) y1, (float) z1};


                MathUtils.normalise(normal);

                verticesBuffer.put((float) x1);
                verticesBuffer.put((float) y1);
                verticesBuffer.put((float) z1);
                normalsBuffer.put((float) normal[0]);
                normalsBuffer.put((float) normal[1]);
                normalsBuffer.put((float) normal[2]);
                float tCoord = 1.0f / maxStacks * i; //Or * 2 to repeat label
                float sCoord = 1.0f / maxSlices * j;
                textureCoords[textureCos++] = sCoord;
                textureCoords[textureCos++] = tCoord;


                //gl.glNormal3dv(normal,0);
                //gl.glVertex3d(x1,y1,z1);
                normal[0] = (float) x2;
                normal[1] = (float) y2;
                normal[2] = (float) z2;

                MathUtils.normalise(normal);
                //gl.glNormal3dv(normal,0);
                //gl.glVertex3d(x2,y2,z2);

                verticesBuffer.put((float) x2);
                verticesBuffer.put((float) y2);
                verticesBuffer.put((float) z2);
                normalsBuffer.put((float) normal[0]);
                normalsBuffer.put((float) normal[1]);
                normalsBuffer.put((float) normal[2]);
                tCoord = 1.0f / maxStacks * (i + 1); //Or * 2 to repeat label
                textureCoords[textureCos++] = sCoord;
                textureCoords[textureCos++] = tCoord;

            }
        }
        verticesBuffer.flip();
        normalsBuffer.flip();


        vertices = verticesBuffer.array();
        normals = normalsBuffer.array();
        indices = new int[vertices.length / 3];

        for (int i = 0; i < indices.length; i++)
            indices[i] = i;

    }


    double r(double t) {
        double x = Math.cos(2 * Math.PI * t);
        return x;
    }

    double getY(double t) {

        double y = Math.sin(2 * Math.PI * t);
        return y;
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
        setSpecularLight(1, 10);
        getRawModel().setMeshMode(GL2.GL_TRIANGLE_STRIP);
    }

    @Override
    protected void setInstance() {
        instance = this;
    }
}
