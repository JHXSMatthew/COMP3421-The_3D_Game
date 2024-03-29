package game.entities;


import com.jogamp.opengl.GL2;
import game.models.presetModels.RoadModel;
import game.render.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * COMMENT: Comment RoadPrototype
 *
 * @author malcolmr
 */
public class RoadPrototype {

    private List<Double> myPoints;
    private double myWidth;
    private RoadModel model;


    /**
     * Create a new road starting at the specified point
     */
    public RoadPrototype(double width, double x0, double y0) {
        myWidth = width;
        myPoints = new ArrayList<Double>();
        myPoints.add(x0);
        myPoints.add(y0);
    }

    /**
     * Create a new road with the specified spine
     *
     * @param width
     * @param spine
     */
    public RoadPrototype(double width, double[] spine) {
        myWidth = width;
        myPoints = new ArrayList<Double>();
        for (int i = 0; i < spine.length; i++) {
            myPoints.add(spine[i]);
        }
    }

    public Entity getRoadEntity(GL2 gl, Loader loader) {
        if (model == null) {
            model = new RoadModel(this);
            model.setUp(gl, loader);
        }
        Entity entity = new Entity(model);
        //entity.move(ArrayUtils.toArray((float) point(0)[0], 0, (float) point(0)[1]));
        return entity;
    }

    /**
     * The width of the road.
     *
     * @return
     */
    public double width() {
        return myWidth;
    }

    /**
     * Add a new segment of road, beginning at the last point added and ending at (x3, y3).
     * (x1, y1) and (x2, y2) are interpolated as bezier control points.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     */
    public void addSegment(double x1, double y1, double x2, double y2, double x3, double y3) {
        myPoints.add(x1);
        myPoints.add(y1);
        myPoints.add(x2);
        myPoints.add(y2);
        myPoints.add(x3);
        myPoints.add(y3);
    }

    /**
     * Get the number of segments in the curve
     *
     * @return
     */
    public int size() {
        return myPoints.size() / 6;
    }

    /**
     * Get the specified control point.
     *
     * @param i
     * @return
     */
    public double[] controlPoint(int i) {
        double[] p = new double[2];
        p[0] = myPoints.get(i * 2);
        p[1] = myPoints.get(i * 2 + 1);
        return p;
    }

    public RoadModel getModel() {
        return model;
    }

    public void setModel(RoadModel model) {
        this.model = model;
    }


    public double[] getMax() {
        double[] currentMax = new double[2];
        currentMax[0] = myPoints.get(0);
        currentMax[1] = myPoints.get(1);
        for (int i = 0; i < myPoints.size(); i++) {
            if (i % 2 == 0) {
                if (currentMax[0] < myPoints.get(i)) {
                    currentMax[0] = myPoints.get(i);
                }

            } else {
                if (currentMax[1] < myPoints.get(i)) {
                    currentMax[1] = myPoints.get(i);
                }
            }
        }
        return currentMax;
    }

    /**
     *
     * @return the min array of this road
     */
    public double[] getMin() {
        double[] currentMin = new double[2];
        currentMin[0] = myPoints.get(0);
        currentMin[1] = myPoints.get(1);
        for (int i = 0; i < myPoints.size(); i++) {
            if (i % 2 == 0) {
                if (currentMin[0] > myPoints.get(i)) {
                    currentMin[0] = myPoints.get(i);
                }

            } else {
                if (currentMin[1] > myPoints.get(i)) {
                    currentMin[1] = myPoints.get(i);
                }
            }
        }
        return currentMin;
    }

    /**
     * Get a point on the spine. The parameter t may vary from 0 to size().
     * Points on the kth segment take have parameters in the range (k, k+1).
     *
     * @param t
     * @return
     */
    public double[] point(double t, boolean model) {
        if (model) {
            return point(t);
        }

        int i = (int) Math.floor(t);
        t = t - i;

        i *= 6;

        double x0 = myPoints.get(i++);
        double y0 = myPoints.get(i++);
        double x1 = myPoints.get(i++);
        double y1 = myPoints.get(i++);
        double x2 = myPoints.get(i++);
        double y2 = myPoints.get(i++);
        double x3 = myPoints.get(i++);
        double y3 = myPoints.get(i++);

        double[] p = new double[2];

        p[0] = b(0, t) * x0 + b(1, t) * x1 + b(2, t) * x2 + b(3, t) * x3;
        p[1] = b(0, t) * y0 + b(1, t) * y1 + b(2, t) * y2 + b(3, t) * y3;

        return p;
    }


    /**
     * Get a point on the spine. The parameter t may vary from 0 to size().
     * Points on the kth segment take have parameters in the range (k, k+1).
     *
     * @param t
     * @return
     */
    public double[] point(double t) {
        int i = (int) Math.floor(t);
        t = t - i;

        i *= 6;

        double x0 = myPoints.get(i++);
        double y0 = myPoints.get(i++);
        double x1 = myPoints.get(i++);
        double y1 = myPoints.get(i++);
        double x2 = myPoints.get(i++);
        double y2 = myPoints.get(i++);
        double x3 = myPoints.get(i++);
        double y3 = myPoints.get(i++);

        double[] p = new double[2];

        p[0] = b(0, t) * x0 + b(1, t) * x1 + b(2, t) * x2 + b(3, t) * x3;
        p[1] = b(0, t) * y0 + b(1, t) * y1 + b(2, t) * y2 + b(3, t) * y3;

        return p;
    }


    /**
     * Calculate the Bezier coefficients
     *
     * @param i
     * @param t
     * @return
     */
    private double b(int i, double t) {

        switch (i) {

            case 0:
                return (1 - t) * (1 - t) * (1 - t);

            case 1:
                return 3 * (1 - t) * (1 - t) * t;

            case 2:
                return 3 * (1 - t) * t * t;

            case 3:
                return t * t * t;
        }

        // this should never happen
        throw new IllegalArgumentException("" + i);
    }


}
