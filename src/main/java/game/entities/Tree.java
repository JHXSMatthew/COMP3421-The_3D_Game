package game.entities;


/**
 * COMMENT: Comment Tree
 *
 * @author malcolmr
 */
public class Tree {

    private float[] myPos;
    private Entity treeTrunk;
    private Entity leaves;
    
    public Tree(float x, float y, float z) {
        myPos = new float[3];
        myPos[0] = x;
        myPos[1] = y;
        myPos[2] = z;
    }
    
    public float[] getPosition() {
        return myPos;
    }


}
