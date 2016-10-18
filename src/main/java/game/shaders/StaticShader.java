package game.shaders;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.math.Matrix4;

/**
 * Created by Matthew on 18/10/2016.
 */
public class StaticShader extends BasicShader  {

    private static final String VERTEX = "src/main/java/game/shaders/StaticVertexShader.GLSL";
    private static final String FRAGMENT = "src/main/java/game/shaders/StaticFragmentShader.GLSL";


    private int location_tMatrix;
    //camera
    private int location_pMatrix;

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
    }

    @Override
    protected void getAllUniformLocations(GL2 gl) {
        location_tMatrix = super.getUniformLocation(gl,"tMatrix");
        location_pMatrix = super.getUniformLocation(gl,"pMatrix");
    }


    public void loadTransformationMatrix(GL2 gl,Matrix4 matrix4){
        super.loadMatrix(gl,location_tMatrix,matrix4);
    }

    public void loadProjectionMatrix(GL2 gl,Matrix4 matrix4){
        super.loadMatrix(gl,location_pMatrix,matrix4);
    }
}
