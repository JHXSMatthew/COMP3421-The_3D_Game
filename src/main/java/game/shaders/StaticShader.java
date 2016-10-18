package game.shaders;

import com.jogamp.opengl.GL2;

/**
 * Created by Matthew on 18/10/2016.
 */
public class StaticShader extends BasicShader  {

    private static final String VERTEX = "src/main/java/game/shaders/StaticVertexShader.GLSL";
    private static final String FRAGMENT = "src/main/java/game/shaders/StaticFragmentShader.GLSL";

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
}
