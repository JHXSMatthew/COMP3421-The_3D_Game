package game.shaders;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.math.Matrix4;
import game.entities.Camera;
import game.entities.Light;
import game.render.Loader;
import game.utils.ArrayUtils;
import game.utils.MathUtils;

import java.util.List;

/**
 * Created by Matthew on 18/10/2016.
 */
public class StaticShader extends BasicShader {

    private static final String VERTEX = "src/main/java/game/shaders/StaticVertexShader.GLSL";
    private static final String FRAGMENT = "src/main/java/game/shaders/StaticFragmentShader.GLSL";
    private static final int MAX_LIGHT_SIZE = 2;

    private int location_tMatrix;
    //camera
    private int location_pMatrix;
    private int location_vMatrix;
    private int location_cameraPosition; //reduce calculation
    //light
    private int[] location_dLight ;
    private int[] location_cLight ;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_ambient;

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
        super.bindAttribute(gl, "position", Loader.MODEL_ATTRIBUTE_POSITION);
        super.bindAttribute(gl, "textureCoords", Loader.MODEL_ATTRIBUTE_TEXTURE);
        super.bindAttribute(gl, "normal", Loader.MODEL_ATTRIBUTE_NORMAL);
    }

    @Override
    protected void getAllUniformLocations(GL2 gl) {
        location_tMatrix = super.getUniformLocation(gl, "tMatrix");
        location_pMatrix = super.getUniformLocation(gl, "pMatrix");
        location_vMatrix = super.getUniformLocation(gl, "vMatrix");


        location_dLight = new int[MAX_LIGHT_SIZE];
        location_cLight = new int[MAX_LIGHT_SIZE];
        location_ambient = super.getUniformLocation(gl,"ambient");
        for(int i = 0 ; i < MAX_LIGHT_SIZE ; i ++){
            location_cLight[i] = super.getUniformLocation(gl, "cLight" + "["+ i + "]");
            location_dLight[i] = super.getUniformLocation(gl, "dLight" + "["+ i + "]");
        }

        location_reflectivity = super.getUniformLocation(gl, "reflectivity");
        location_shineDamper = super.getUniformLocation(gl, "shineDamper");
        location_cameraPosition = super.getUniformLocation(gl, "pCamera");


    }


    public void loadTransformationMatrix(GL2 gl, Matrix4 matrix4) {
        super.loadMatrix(gl, location_tMatrix, matrix4);
    }

    public void loadProjectionMatrix(GL2 gl, Matrix4 matrix4) {
        super.loadMatrix(gl, location_pMatrix, matrix4);
    }

    public void loadLight(GL2 gl, List<Light> lights) {
        for(int i = 0 ; i < MAX_LIGHT_SIZE ; i ++){
            if(i < lights.size() ){

                super.loadVector(gl, location_cLight[i], lights.get(i).getColor());
                super.loadVector(gl, location_dLight[i], lights.get(i).getDirection());
            }else{

                super.loadVector(gl, location_cLight[i], ArrayUtils.toArray(0,0,0));
                super.loadVector(gl, location_dLight[i], ArrayUtils.toArray(0,0,0));
            }
        }

    }

    public void loadSpecularLightVars(GL2 gl, float damper, float reflectivity) {
        super.loadFloat(gl, location_shineDamper, damper);
        super.loadFloat(gl, location_reflectivity, reflectivity);
    }

    public void loadViewMatrix(GL2 gl, Camera c) {
        super.loadMatrix(gl, location_vMatrix, MathUtils.createViewMatrix(c));
        super.loadVector(gl, location_cameraPosition, c.getPosition());
    }

    public void loadAmbient(GL2 gl, float ambient){
        super.loadFloat(gl,location_ambient,ambient);
    }

}
