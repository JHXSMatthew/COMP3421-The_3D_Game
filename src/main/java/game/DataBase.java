package game;

import game.entities.TreePrototype;
import game.models.RoadModel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;



/**
 * COMMENT: Comment HeightMap 
 *
 * @author malcolmr
 */
public class DataBase {

    private Dimension mySize;
    private double[][] myAltitude;
    private List<TreePrototype> myTrees;
    private List<RoadModel> myRoads;
    private float[] mySunlight;

    /**
     * Create a new terrain
     *
     * @param width The number of vertices in the x-direction
     * @param depth The number of vertices in the z-direction
     */
    public DataBase(int width, int depth) {
        mySize = new Dimension(width, depth);
        myAltitude = new double[width][depth];
        myTrees = new ArrayList<TreePrototype>();
        myRoads = new ArrayList<RoadModel>();
        mySunlight = new float[3];
    }
    
    public DataBase(Dimension size) {
        this(size.width, size.height);
    }

    public Dimension size() {
        return mySize;
    }

    public List<TreePrototype> trees() {
        return myTrees;
    }

    public List<RoadModel> roads() {
        return myRoads;
    }

    public float[] getSunlight() {
        return mySunlight;
    }

    /**
     * Set the sunlight direction. 
     * 
     * Note: the sun should be treated as a directional light, without a position
     * 
     * @param dx
     * @param dy
     * @param dz
     */
    public void setSunlightDir(float dx, float dy, float dz) {
        mySunlight[0] = dx;
        mySunlight[1] = dy;
        mySunlight[2] = dz;        
    }
    
    /**
     * Resize the terrain, copying any old altitudes. 
     * 
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        mySize = new Dimension(width, height);
        double[][] oldAlt = myAltitude;
        myAltitude = new double[width][height];
        
        for (int i = 0; i < width && i < oldAlt.length; i++) {
            for (int j = 0; j < height && j < oldAlt[i].length; j++) {
                myAltitude[i][j] = oldAlt[i][j];
            }
        }
    }

    /**
     * Get the altitude at a grid point
     * 
     * @param x
     * @param z
     * @return
     */
    public double getGridAltitude(int x, int z) {
        return myAltitude[x][z];
    }

    /**
     * Set the altitude at a grid point
     * 
     * @param x
     * @param z
     * @return
     */
    public void setGridAltitude(int x, int z, double h) {
        myAltitude[x][z] = h;
    }

    /**
     * Get the altitude at an arbitrary point. 
     * Non-integer points should be interpolated from neighbouring grid points
     * 
     * TO BE COMPLETED
     * 
     * @param x
     * @param z
     * @return
     */
    public double altitude(double x, double z) {
        double altitude = 0;

        
        
        return altitude;
    }

    /**
     * Add a tree at the specified (x,z) point. 
     * The tree's y coordinate is calculated from the altitude of the terrain at that point.
     * 
     * @param x
     * @param z
     */
    public void addTree(double x, double z) {
        double y = altitude(x, z);
        TreePrototype tree = new TreePrototype((float)x, (float)y, (float)z);
        myTrees.add(tree);
    }


    /**
     * Add a road. 
     * 
     * @param width
     * @param spine
     */
    public void addRoad(double width, double[] spine) {
        RoadModel road = new RoadModel(width, spine);
        myRoads.add(road);        
    }

    public float[][] getAttribute(){
        float[][] r = new float[myAltitude.length][myAltitude.length];
        for(int i = 0 ; i < myAltitude.length ; i ++){
            for(int j = 0 ; j < myAltitude.length ; j++){
                r[i][j] = (float)myAltitude[i][j];
            }
        }
        return r;
    }

/*
    @Override
    public void draw(GL2 gl) {
        for(int i = 0 ; i < size().getWidth() ; i ++){
            for(int j = 0 ; j < size().getHeight() ; j ++){

                double[] p1 = MathUtils.createDouble(i,j,getGridAltitude(i,j),true);;
                double[] p2;
                double[] p3;
                double[] normal;
                //draw up one
                if(i + 1 < size().getWidth() && j > 0 && j+1 < size().getHeight()){
                    p2 = MathUtils.createDouble(i,j-1,getGridAltitude(i,j-1),true);
                    p3 = MathUtils.createDouble(i+1,j,getGridAltitude(i+1,j),true);
                    normal = MathUtils.getNormal(p1,p2,p3);
                    drawTangleMesh(gl,p1,p2,p3,normal);
                }
                //draw the other one
                if(j + 1 < size().getHeight() && i - 1 >= 0) {
                    p2 = MathUtils.createDouble(i-1,j+1,getGridAltitude(i-1,j+1),true);
                    p3 = MathUtils.createDouble(i,j+1,getGridAltitude(i,j+1),true);
                    normal = MathUtils.getNormal(p1,p2,p3);
                    drawTangleMesh(gl,p1,p2,p3,normal);
                }
            }
        }

        for(Drawable drawable :trees()){
            drawable.draw(gl);
        }
        for(Drawable drawable :roads()){
            drawable.draw(gl);
        }
    }
*/


}
