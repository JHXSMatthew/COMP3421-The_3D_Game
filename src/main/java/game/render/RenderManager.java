package game.render;


import com.jogamp.opengl.GL2;
import game.entities.Camera;
import game.entities.Entity;
import game.entities.Light;
import game.models.TexturedModel;
import game.shaders.StaticShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 19/10/2016.
 */
public class RenderManager {

    private StaticShader shader;
    private Render render;

    private Map<TexturedModel,List<Entity>> entitiesBuffer;

    public RenderManager(GL2 gl){
        shader = new StaticShader(gl);
        render = new Render(shader);
        entitiesBuffer = new HashMap<>();
    }

    public void render(GL2 gl, Light light, Camera camera){
        render.prepare(gl);
        shader.start(gl);
        camera.move();
        shader.loadViewMatrix(gl,camera);
        shader.loadLight(gl,light);

        render.render(gl, entitiesBuffer);

        shader.stop(gl);
        entitiesBuffer.clear();

    }

    public void addEntity(List<Entity> list){
        for(Entity entity : list){
            TexturedModel model = entity.getModel();
            List<Entity> l = entitiesBuffer.get(model);
            if(l == null)
                l = new ArrayList<>();
            l.add(entity);
            entitiesBuffer.put(model,l);
        }
    }

    public void updatePerspectiveCamera(GL2 gl,int width, int height) {
        render.updatePerspectiveCamera(gl,width,height);
    }

        public StaticShader getShader(){
        return shader;
    }


    public void dispose(GL2 gl){
        shader.dispose(gl);
    }
}
