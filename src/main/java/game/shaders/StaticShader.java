package game.shaders;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.math.Matrix4;
import game.entities.Camera;
import game.entities.Light;
import game.utils.MathUtils;

/**
 * Created by Matthew on 18/10/2016.
 */
public class StaticShader extends BasicShader  {

    private static final String VERTEX = "src/main/java/game/shaders/StaticVertexShader.GLSL";
    private static final String FRAGMENT = "src/main/java/game/shaders/StaticFragmentShader.GLSL";


    private int location_tMatrix;
    //camera
    private int location_pMatrix;
    private int location_vMatrix;
    //light
    private int location_pLight;
    private int location_cLight;

    /**
     * wrapper of OpenGL shader program
     *
     * @param gl
     **/

    public StaticShader(GL2 gl) {
        super(gl, VERTEX, FRAGMENT);
    }

    @Override
    protected void bindAttributes(GL2 gl) {
        super.bindAttribute(gl,"position",0);
        super.bindAttribute(gl,"textureCoords",1);
        super.bindAttribute(gl,"normal",2);
    }

    @Override
    protected void getAllUniformLocations(GL2 gl) {
        location_tMatrix = super.getUniformLocation(gl,"tMatrix");
        location_pMatrix = super.getUniformLocation(gl,"pMatrix");
        location_vMatrix = super.getUniformLocation(gl,"vMatrix");
        location_cLight = super.getUniformLocation(gl,"cLight");
        location_pLight = super.getUniformLocation(gl,"pLight");
    }


    public void loadTransformationMatrix(GL2 gl,Matrix4 matrix4){
        super.loadMatrix(gl,location_tMatrix,matrix4);
    }

    public void loadProjectionMatrix(GL2 gl,Matrix4 matrix4){
        super.loadMatrix(gl,location_pMatrix,matrix4);
    }

    public void loadLight(GL2 gl,Light l){
        super.loadVector(gl,location_cLight,l.getColor());
        super.loadVector(gl,location_pLight,l.getPosition());

    }


    public void loadViewMatrix(GL2 gl, Camera c){
        super.loadMatrix(gl,location_vMatrix, MathUtils.createViewMatrix(c));
    }

}
