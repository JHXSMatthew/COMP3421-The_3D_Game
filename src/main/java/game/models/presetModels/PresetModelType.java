package game.models.presetModels;

import java.lang.reflect.Field;

/**
 * Created by Matthew on 19/10/2016.
 */
public enum PresetModelType {



    Terrain(TerrainModel.class),
    TreeLeaves(TreeLeavesModel.class);

    private Class<? extends PresetModel> clazz;

    PresetModelType(Class<? extends PresetModel> modelClass){
        this.clazz = modelClass;
    }

    public PresetModel getModel(){
        try {
            Field f =  clazz.getDeclaredField("instance");
            f.setAccessible(true);
            return (PresetModel) f.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
