package game.render;

import com.jogamp.opengl.math.Matrix4;
import game.entities.Entity;
import game.models.RawModel;
import com.jogamp.opengl.GL2;
import game.models.Renderable;
import game.models.TexturedModel;
import game.shaders.StaticShader;
import game.utils.MathUtils;

/**
 * Created by Matthew on 30/09/2016.
 * The render class to render models.
 */
public class Render {


    private Matrix4 pMatrix;

    public Render(){

    }



    public void prepare(GL2 gl2){
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl2.glClearColor(1,0,0,0);
    }

    public void render(GL2 gl, Renderable m, StaticShader shader){
        RawModel model = m.getRawModel();

        gl.glBindVertexArray(model.getVaoID());
        gl.glEnableVertexAttribArray(Loader.MODEL_ATTRIBUTE_POSITION);
        gl.glEnableVertexAttribArray(Loader.TEXTURE_ATTRIBUTE_POSITION);
        //gl.glDrawArrays(GL2.GL_TRIANGLES, 0 , model.getVertexCount());

        if(m instanceof Entity){
            Matrix4 tMatrix = MathUtils.createTransforMatrix(((Entity) m).getPosition(),
                    ((Entity) m).getRotation(),
                    ((Entity) m).getScale());
            shader.loadTransformationMatrix(gl,tMatrix);
            loadTexture(gl,((Entity) m).getModel());
        }

        if(m instanceof TexturedModel) {
            loadTexture(gl,(TexturedModel) m);
        }
        gl.glDrawElements(GL2.GL_TRIANGLES , model.getVertexCount() , GL2.GL_UNSIGNED_INT , 0);
        gl.glDisableVertexAttribArray(Loader.MODEL_ATTRIBUTE_POSITION);
        gl.glDisableVertexAttribArray(Loader.TEXTURE_ATTRIBUTE_POSITION);
        gl.glBindVertexArray(0);


    }

    private void loadTexture(GL2 gl,TexturedModel model){
        gl.glActiveTexture(GL2.GL_TEXTURE0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D,model.getTexture().getID() );
    }

    //camera constants
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    public void updatePerspectiveCamera(int width, int height,StaticShader shader,GL2 gl) {
        float aspectRation = (float) width / (float) height;
        float y_scale = (float)(1f/Math.tan(Math.toRadians(FOV/2f))) * aspectRation;
        float x_scale = y_scale/aspectRation;
        float frustum_legth  = FAR_PLANE - NEAR_PLANE;

        pMatrix = new Matrix4();
        pMatrix.makePerspective(FOV,aspectRation,NEAR_PLANE,FAR_PLANE);

        shader.start(gl);
        shader.loadProjectionMatrix(gl,pMatrix);
        shader.stop(gl);
    }
}
