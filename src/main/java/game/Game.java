package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ass2.spec.LevelIO;
import ass2.spec.Terrain;
import game.models.RawModel;
import game.models.Renderable;
import game.models.TexturedModel;
import game.render.Loader;
import game.render.Render;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;
import game.shaders.StaticShader;
import game.textures.ModelTexture;


/**
 * COMMENT: Comment Game 
 *
 * @author malcolmr
 */
public class Game extends JFrame implements GLEventListener{

    private Terrain myTerrain;
    private Render render;
    private Loader loader;
    private StaticShader shader;
    private List<Renderable> models = new ArrayList<Renderable>();

    public Game(Terrain terrain) {
    	super("Assignment 2");
        myTerrain = terrain;
   
    }
    
    /** 
     * Run the game.
     *
     */
    public void run() {
    	  GLProfile glp = GLProfile.getDefault();
          GLCapabilities caps = new GLCapabilities(glp);
          GLJPanel panel = new GLJPanel();
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
        Terrain terrain = LevelIO.load(new File(args[0]));
        Game game = new Game(terrain);
        game.run();
    }


	@Override
	public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        render.prepare(gl);

        for(Renderable model : models){
            shader.start(gl);
            render.render(gl,model);
            shader.stop(gl);
        }

    }

	@Override
	public void dispose(GLAutoDrawable drawable) {
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

	@Override
	public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        loader = new Loader();
        render = new Render();
        shader = new StaticShader(gl);
        RawModel model = loader.loadModel(gl,vertices,indices,null);
        ModelTexture texture =  new ModelTexture(loader.loadTexture(gl,"grass.jpg"));
        TexturedModel texturedModel = new TexturedModel(model,texture);
        models.add(texturedModel);
    }

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {


    }

}
