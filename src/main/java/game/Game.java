package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import game.entities.CameraFreeMove;
import game.entities.Light;
import game.models.presetModels.PresetModelType;
import game.models.presetModels.TerrainModel;
import game.render.RenderManager;
import game.utils.IOUtils;
import game.entities.Camera;
import game.entities.Entity;
import game.render.Loader;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;
import game.utils.ArrayUtils;


/**
 * COMMENT: Comment Game 
 *
 * @author malcolmr
 */
public class Game extends JFrame implements GLEventListener{

    private DataBase data;
    private RenderManager render;
    private Loader loader;

    private List<Entity> entities = new ArrayList<Entity>();
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
          camera = new CameraFreeMove();
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
        render.addEntity(entities);
        render.render(gl,light,camera);
    }

	@Override
	public void dispose(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        render.dispose(gl);
        loader.dispose(gl);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        loader = new Loader();
        render = new RenderManager(gl);
        loadModels(gl);
        light = new Light(data.getSunlight(),ArrayUtils.toArray(1,1,1));
        camera.setPosition(ArrayUtils.toArray(0f,0.5f,9f));
        Entity terrain  = new Entity(PresetModelType.Terrain.getModel().getTextureModel());
        entities.add(terrain);

    }



    @Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
        GL2 gl = drawable.getGL().getGL2();
        render.updatePerspectiveCamera(gl,width, height);
    }

    private void loadModels(GL2 gl){
        new TerrainModel(data.getAttribute()).setUp(gl,loader);


    }

}
