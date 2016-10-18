package game.models;

/**
 * Created by Matthew on 30/09/2016.
 */
public class RawModel implements Renderable {

    private int vaoID ;
    private int vertexCount = 0;

    public RawModel(int vaoID , int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID(){
        return vaoID;
    }

    public int getVertexCount(){
        return vertexCount;
    }

}
