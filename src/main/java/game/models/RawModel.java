package game.models;

import com.jogamp.opengl.GL2;

/**
 * Created by Matthew on 30/09/2016.
 */
public class RawModel implements IRenderable {

    private int vaoID;
    private int vertexCount = 0;


    private int meshMode = GL2.GL_TRIANGLES;

    public RawModel(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getMeshMode() {
        return meshMode;
    }

    public void setMeshMode(int meshMode) {
        this.meshMode = meshMode;
    }

    @Override
    public RawModel getRawModel() {
        return this;
    }
}
