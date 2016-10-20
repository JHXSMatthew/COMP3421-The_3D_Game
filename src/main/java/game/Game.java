package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import game.entities.*;
import game.models.ITexturable;
import game.models.TexturedModel;
import game.models.presetModels.PresetModelType;
import game.models.presetModels.TerrainModel;
import game.models.presetModels.TreeLeavesModel;
import game.models.presetModels.TreeTrunkModel;
import game.render.RenderManager;
import game.textures.ModelTexture;
import game.utils.IOUtils;
import game.render.Loader;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;
import game.utils.ArrayUtils;
import game.utils.OBJUtils;


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
    private Avatar avatar;

    private long lastFrame;

    private GLJPanel panel;

    private static Game instance;


    public Game(DataBase terrain) {
    	super("Assignment 2");
        data = terrain;
        instance = this;
   
    }
    
    /** 
     * Run the game.
     *
     */
    public void run() {
    	  GLProfile glp = GLProfile.getDefault();
          GLCapabilities caps = new GLCapabilities(glp);
          panel = new GLJPanel();
          camera = new Camera();
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
        updateAvatarMovement();
        render.addEntity(entities);
        render.render(gl,light,camera);

    }

    private void updateAvatarMovement(){
        if(lastFrame == 0){
            lastFrame = System.currentTimeMillis();
        }
        long current = System.currentTimeMillis();
        float passed = ((float)(current - lastFrame))/1000f;

        lastFrame = current;
        avatar.updateLocation(passed);
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
        for(TreeWrapper prototype : data.trees()){
            prototype.register();
        }
        //camera.setPosition(ArrayUtils.toArray(0f,0.5f,9f));
        addNewEntity(PresetModelType.Terrain.getModel());
        for(RoadPrototype prototype : data.roads()){
            addNewEntity(prototype.getRoadEntity(gl,loader));
        }
        avatar = new Avatar(new TexturedModel(OBJUtils.loadRawModel(gl,"stall.obj",loader),new ModelTexture(loader.loadTexture(gl,"stallTexture.png"))));

        panel.addKeyListener(avatar);
        addNewEntity(avatar);

    }


    public Entity addNewEntity(ITexturable model){
        Entity e = new Entity(model);
        entities.add(e);
        return e;
    }

    public Entity addNewEntity(Entity entity){
        entities.add(entity);
        return entity;
    }



    @Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
        GL2 gl = drawable.getGL().getGL2();
        render.updatePerspectiveCamera(gl,width, height);
    }

    private void loadModels(GL2 gl){
        new TerrainModel(data.getAttribute()).setUp(gl,loader);
        new TreeLeavesModel().setUp(gl,loader);
        new TreeTrunkModel().setUp(gl,loader);

    }

    public float getAltitude(float x , float z){
        return (float)data.altitude(x,z);
    }

    public static Game getGame(){
        return instance;
    }



}
