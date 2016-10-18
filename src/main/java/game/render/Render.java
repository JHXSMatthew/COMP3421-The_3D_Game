package game.render;

import game.models.RawModel;
import com.jogamp.opengl.GL2;
import game.models.Renderable;
import game.models.TexturedModel;

/**
 * Created by Matthew on 30/09/2016.
 * The render class to render models.
 */
public class Render {

    public Render(){

    }

    public void prepare(GL2 gl2){
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl2.glClearColor(1,0,0,0);
    }

    public void render(GL2 gl,Renderable m){
        RawModel model = null;
        if(m instanceof RawModel){
            model = (RawModel)m;
        }else if(m instanceof TexturedModel){
            model = ((TexturedModel)m).getModel();
        }


        gl.glBindVertexArray(model.getVaoID());
        gl.glEnableVertexAttribArray(Loader.ATTRIBUTE_POSITION);
        //gl.glDrawArrays(GL2.GL_TRIANGLES, 0 , model.getVertexCount());
        gl.glDrawElements(GL2.GL_TRIANGLES , model.getVertexCount() , GL2.GL_UNSIGNED_INT , 0);
        gl.glDisableVertexAttribArray(Loader.ATTRIBUTE_POSITION);
        gl.glBindVertexArray(0);


    }


}
