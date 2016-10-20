package game.render;

import com.jogamp.opengl.math.Matrix4;
import game.entities.Entity;
import game.models.RawModel;
import com.jogamp.opengl.GL2;
import game.models.TexturedModel;
import game.shaders.StaticShader;
import game.utils.MathUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 30/09/2016.
 * The render class to render models.
 */
public class Render {


    private Matrix4 pMatrix;
    private StaticShader shader;

    public Render(StaticShader shader){
        this.shader = shader;
    }



    public void prepare(GL2 gl2){
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        /*gl2.glEnable(GL.GL_CULL_FACE);
        gl2.glCullFace(GL.GL_BACK);*/
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl2.glClearColor(0,0,0,0);
    }

    public void render(GL2 gl,Map<TexturedModel,List<Entity>> entities){
        for(TexturedModel model : entities.keySet()){
            //prepare
            loaModel(gl,model);
            loadTexture(gl,model);

            List<Entity> list = entities.get(model);
            for(Entity entity : list){
                if(!entity.shouldShow()){
                    continue;
                }
                loadEntity(gl,entity);
                //render
                gl.glDrawElements(GL2.GL_TRIANGLES , entity.getRawModel().getVertexCount() , GL2.GL_UNSIGNED_INT , 0);
            }
            unloadModel(gl);
        }

    }

    private void unloadModel(GL2 gl){
        gl.glDisableVertexAttribArray(Loader.MODEL_ATTRIBUTE_POSITION);
        gl.glDisableVertexAttribArray(Loader.MODEL_ATTRIBUTE_TEXTURE);
        gl.glDisableVertexAttribArray(Loader.MODEL_ATTRIBUTE_NORMAL);
        gl.glBindVertexArray(0);
    }

    private void loaModel(GL2 gl,TexturedModel m){
        RawModel model = m.getRawModel();

        gl.glBindVertexArray(model.getVaoID());
        gl.glEnableVertexAttribArray(Loader.MODEL_ATTRIBUTE_POSITION);
        gl.glEnableVertexAttribArray(Loader.MODEL_ATTRIBUTE_TEXTURE);
        gl.glEnableVertexAttribArray(Loader.MODEL_ATTRIBUTE_NORMAL);
    }

    private void loadTexture(GL2 gl,TexturedModel model){
        gl.glActiveTexture(GL2.GL_TEXTURE0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D,model.getTexture().getID() );
        shader.loadSpecularLightVars(gl,model.getTexture().getShineDamper(),model.getTexture().getReflectivity());
    }

    private void loadEntity(GL2 gl,Entity m){
        Matrix4 tMatrix = MathUtils.createTransforMatrix(m.getPosition(),
                 m.getRotation(),
                 m.getScale());
        shader.loadTransformationMatrix(gl,tMatrix);
    }



    //camera constants
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    public void updatePerspectiveCamera(GL2 gl,int width, int height) {
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
