package game.entities;


import game.Config;
import game.Game;
import game.models.OBJTypes;
import game.models.presetModels.PresetModelType;
import game.utils.ArrayUtils;

/**
 * COMMENT: Comment TreeWrapper
 *
 * @author malcolmr
 */
public class TreeWrapper {

    Entity treeTrunk;
    Entity treeLeaves;
    private float[] myPos;

    public TreeWrapper(float x, float y, float z) {
        myPos = new float[3];
        myPos[0] = x;
        myPos[1] = y;
        myPos[2] = z;
    }

    public void register() {
        if (!Config.advancedTree) {
            treeTrunk = new Entity(PresetModelType.TreeTrunk.getModel());
            treeLeaves = new Entity(PresetModelType.TreeLeaves.getModel());
            treeLeaves.move(ArrayUtils.toArray(0, 1.5f, 0));
            treeTrunk.move(ArrayUtils.toArray(0, 1.5f, 0));
            treeTrunk.rotate(ArrayUtils.toArray(-90, 0, 0));
            treeTrunk.setScale(ArrayUtils.toArray(0.15f, 0.15f, 0.5f));

            treeTrunk.move(myPos);
            treeLeaves.move(myPos);
            Game.getGame().addNewEntity(treeLeaves);
            Game.getGame().addNewEntity(treeTrunk);
        } else {
            Entity entity = Game.getGame().addNewEntity(OBJTypes.ObjTree);
            entity.move(myPos);
        }

    }


    public float[] getPosition() {
        return myPos;
    }


}
