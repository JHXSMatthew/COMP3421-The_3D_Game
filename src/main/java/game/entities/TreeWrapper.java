package game.entities;


import game.Game;
import game.models.presetModels.PresetModelType;
import game.utils.ArrayUtils;

/**
 * COMMENT: Comment TreeWrapper
 *
 * @author malcolmr
 */
public class TreeWrapper {

    private static final boolean stupidTree = true;

    private float[] myPos;
    Entity treeTrunk;
    Entity treeLeaves;

    public TreeWrapper(float x, float y, float z) {
        myPos = new float[3];
        myPos[0] = x;
        myPos[1] = y;
        myPos[2] = z;
    }

    public void register(){
        if(stupidTree){
            treeTrunk = new Entity(PresetModelType.TreeTrunk.getModel());
            treeLeaves = new Entity(PresetModelType.TreeLeaves.getModel());
            treeLeaves.move(ArrayUtils.toArray(0,1.5f,0));
            treeTrunk.move(ArrayUtils.toArray(0,1.5f,0));
            treeTrunk.rotate(ArrayUtils.toArray(-90,0,0));
            treeTrunk.setScale(ArrayUtils.toArray(0.15f,0.15f,0.5f));

            treeTrunk.move(myPos);
            treeLeaves.move(myPos);
            Game.getGame().addNewEntity(treeLeaves);
            Game.getGame().addNewEntity(treeTrunk);
        }

    }



    
    public float[] getPosition() {
        return myPos;
    }


}
