package game.textures;

/**
 * Created by Matthew on 18/10/2016.
 * the texture of model,
 */
public class ModelTexture {

    private int textureID;

    //specular lighting
    private float shineDamper = 1;
    private float reflectivity = 0;

    public ModelTexture(int id) {
        this.textureID = id;
    }

    /**
     *
     * @return id
     */
    public int getID() {
        return this.textureID;
    }

    /**
     *
     * @return the brightness
     */
    public float getShineDamper() {
        return shineDamper;
    }

    /**
     *
     * @param shineDamper the brightness
     */
    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    /**
     *
     * @return the Reflectivity
     */
    public float getReflectivity() {
        return reflectivity;
    }

    /**
     *
     * @param reflectivity the Reflectivity
     */
    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
