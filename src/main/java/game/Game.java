package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import game.entities.CameraFreeMove;
import game.entities.Light;
import game.models.RawModel;
import game.models.TerrainModel;
import game.models.TexturedModel;
import game.textures.ModelTexture;
import game.utils.IOUtils;
import game.entities.Camera;
import game.entities.Entity;
import game.models.IRenderable;
import game.render.Loader;
import game.render.Render;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;
import game.shaders.StaticShader;
import game.utils.ArrayUtils;


/**
 * COMMENT: Comment Game 
 *
 * @author malcolmr
 */
public class Game extends JFrame implements GLEventListener{

    private DataBase data;
    private Render render;
    private Loader loader;
    private StaticShader shader;

    private List<IRenderable> models = new ArrayList<IRenderable>();
    private Camera camera;
    private Light light;

    public Game(DataBase terrain) {
    	super("Assignment 2");
        data = terrain;
   
    }
    
    /** 
     * Run the game.
     *
     */
    public void run() {
    	  GLProfile glp = GLProfile.getDefault();
          GLCapabilities caps = new GLCapabilities(glp);
          GLJPanel panel = new GLJPanel();
          camera = new Camera();
          panel.addKeyListener(camera);
          panel.addGLEventListener(this);
 
          // Add an animator to call 'display' at 60fps        
          FPSAnimator animator = new FPSAnimator(60);
          animator.add(panel);
          animator.start();

          getContentPane().add(panel);
          setSize(800, 600);        
          setVisible(true);
          setDefaultCloseOperation(EXIT_ON_CLOSE);        
    }
    
    /**
     * Load a level file and display it.
     * 
     * @param args - The first argument is a level file in JSON format
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        DataBase terrain = IOUtils.load(new File(args[0]));
        Game game = new Game(terrain);
        game.run();
    }


	@Override
	public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        render.prepare(gl);
        for(IRenderable model : models){
            shader.start(gl);
            shader.loadLight(gl,light);
            camera.move();
            shader.loadViewMatrix(gl,camera);
            render.render(gl,model,shader);
            shader.stop(gl);
        }

    }

	@Override
	public void dispose(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        shader.dispose(gl);
        loader.dispose(gl);
		// TODO Auto-generated method stub
		
	}

    float[] vertices = {
            -0.5f, 0.5f, 0,
            -0.5f, -0.5f, 0,
            0.5f, -0.5f, 0,
            0.5f, 0.5f, 0f
    };

    int[] indices = {
            0,1,3,
            3,1,2
    };
    float[] textureCoords = {
            0,0,
            0,1,
            1,1,
            1,0
    };

	@Override
	public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        loader = new Loader();
        render = new Render();
        shader = new StaticShader(gl);
        loadModels(gl);
        light = new Light(data.getSunlight(),ArrayUtils.toArray(1,1,1));
        /*
        RawModel model = loader.loadToVAO(gl,vertices,indices,textureCoords,null);
        ModelTexture texture =  new ModelTexture(loader.loadTexture(gl,"grass.jpg"));
        TexturedModel texturedModel = new TexturedModel(model,texture);
        Entity entity = new Entity(texturedModel, ArrayUtils.toArray(0,0,0),ArrayUtils.toArray(0,0,0),ArrayUtils.toArray(1,1,1));
*/
        camera.setPosition(ArrayUtils.toArray(0f,0.5f,9f));
        Entity terrain  = new Entity(TerrainModel.getModel().getTextureModel());
        models.add(terrain);
       // models.add(entity);

    }



    @Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
        GL2 gl = drawable.getGL().getGL2();
        render.updatePerspectiveCamera(width, height,shader,gl);
    }

    private void loadModels(GL2 gl){
        TerrainModel terrianModel = new TerrainModel(gl,data.getAttribute(),loader);

    }

}
