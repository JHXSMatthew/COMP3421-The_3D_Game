package game;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import game.entities.*;
import game.models.ITexturable;
import game.models.OBJTypes;
import game.models.TexturedModel;
import game.models.presetModels.PresetModelType;
import game.models.presetModels.TerrainModel;
import game.models.presetModels.TreeLeavesModel;
import game.models.presetModels.TreeTrunkModel;
import game.render.Loader;
import game.render.RenderManager;
import game.textures.ModelTexture;
import game.utils.ArrayUtils;
import game.utils.IOUtils;
import game.utils.OBJUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * COMMENT: Comment Game
 *
 * @author malcolmr
 */
public class Game extends JFrame implements GLEventListener {

    private static Game instance;
    private DataBase data;
    private RenderManager render;
    private Loader loader;
    private List<Entity> entities = new ArrayList<Entity>();
    private Camera camera;
    private List<Light> light;
    private Light globalLight;
    private Avatar avatar;
    private long lastFrame;
    private GLJPanel panel;
    private Ticker ticker;


    public Game(DataBase terrain) {
        super("Assignment 2");
        data = terrain;
        instance = this;

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

    public static Game getGame() {
        return instance;
    }

    /**
     * Run the game.
     */
    public void run() {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        panel = new GLJPanel();
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

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        ticker.update();
        globalLight.setPosition(ticker.getTime());
        globalLight.setColor(ticker.getTime());
        Light.setAmbient(ticker.getTime());
        updateAvatarMovement();
        render.addEntity(entities);
        render.render(gl, light, Light.getAmbient(),camera);

    }

    private void updateAvatarMovement() {
        if (lastFrame == 0) {
            lastFrame = System.currentTimeMillis();
        }
        long current = System.currentTimeMillis();
        float passed = ((float) (current - lastFrame)) / 1000f;

        lastFrame = current;
        avatar.updateLocation(passed);
    }

    public void setTime(int time){
        ticker.setTime(time);
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

        light = new ArrayList<>();
        globalLight = new Light(data.getSunlight(), ArrayUtils.toArray(1, 1, 1));
        Light torch = new Light(ArrayUtils.toArray(0,0,0),ArrayUtils.toArray(1,1,1));
        light.add(globalLight);
        light.add(torch);

        addNewEntity(PresetModelType.Terrain.getModel());

        for (TreeWrapper prototype : data.trees()) {
            prototype.register();
        }
        for (RoadPrototype prototype : data.roads()) {
            Entity road = addNewEntity(prototype.getRoadEntity(gl, loader));
            road.move(ArrayUtils.toArray(0f, 0.02f, 0f));
        }

        for(Float[] stall : data.getStall()){
            Entity s = addNewEntity(OBJTypes.ObjStall);
            s.setScale(ArrayUtils.toArray(0.1f,0.1f,0.1f));
            s.rotate(ArrayUtils.toArray(0,15,0));
            s.move(ArrayUtils.toArray(stall[0],getAltitude(stall[0],stall[1]),stall[1]));
            addNewEntity(s);
        }
        avatar = new Avatar(OBJTypes.ObjTree,torch);

        panel.addKeyListener(avatar);
        addNewEntity(avatar);
        camera.setAvatar(avatar);
        ticker = new Ticker();
    }

    public Entity addNewEntity(ITexturable model) {
        Entity e = new Entity(model);
        entities.add(e);
        return e;
    }

    public Entity addNewEntity(Entity entity) {
        entities.add(entity);
        return entity;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                        int height) {
        GL2 gl = drawable.getGL().getGL2();
        render.updatePerspectiveCamera(gl, width, height);
    }

    private void loadModels(GL2 gl) {
        new TerrainModel(data.getAttribute()).setUp(gl, loader);
        new TreeLeavesModel().setUp(gl, loader);
        new TreeTrunkModel().setUp(gl, loader);

        //obj models
        for(OBJTypes types : OBJTypes.values()){
            types.load(gl,loader);
        }

    }

    public float getAltitude(float x, float z) {
        return (float) data.altitude(x, z);
    }


}
