package game.models;

import com.jogamp.opengl.GL2;

/**
 * COMMENT: Comment TreeModel
 *
 * @author malcolmr
 */
public class TreeModel {

    private double[] myPos;
    
    public TreeModel(double x, double y, double z) {
        myPos = new double[3];
        myPos[0] = x;
        myPos[1] = y;
        myPos[2] = z;
    }
    
    public double[] getPosition() {
        return myPos;
    }


}
