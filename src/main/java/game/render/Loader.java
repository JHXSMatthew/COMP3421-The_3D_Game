package game.render;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import game.Config;
import game.models.RawModel;
import game.utils.ArrayUtils;
import game.utils.BufferUtils;
import game.utils.MathUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 30/09/2016.
 * The loader class to load all model into VAO
 */
public class Loader {

    public final static int MODEL_ATTRIBUTE_POSITION = 0;
    public final static int MODEL_ATTRIBUTE_TEXTURE = 1;
    public final static int MODEL_ATTRIBUTE_NORMAL = 2;


    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<>();


    /**
     * @param gl        the gl
     * @param positions vertex array
     * @param indices   indices array
     * @param texture   texture "location" array
     * @return the model
     */
    public RawModel loadToVAO(GL2 gl, float[] positions, int[] indices, float[] texture, float[] normals) {
        int vaoID = createVAO(gl);
        storeVBOArrayBuffer(gl, MODEL_ATTRIBUTE_POSITION, positions, 3);
        storeVBOArrayBuffer(gl, MODEL_ATTRIBUTE_TEXTURE, texture, 2);
        storeVBOArrayBuffer(gl, MODEL_ATTRIBUTE_NORMAL, normals, 3);
        bindIndicesBuffer(gl, indices);
        unbindVAO(gl);
        return new RawModel(vaoID, indices.length);
    }


    public int loadTexture(GL2 gl, String fileName) {
        TextureData data = null;

        try {
            File file = new File(fileName);
            BufferedImage img = ImageIO.read(file); // read file into BufferedImage
            ImageUtil.flipImageVertically(img);

            //This library call flips all images the same way
            data = AWTTextureIO.newTextureData(GLProfile.getDefault(), img, false);

        } catch (IOException exc) {
            System.err.println(fileName);
            exc.printStackTrace();
            System.exit(1);
        }
        int[] textureID = new int[1];
        gl.glGenTextures(1, textureID, 0);
        //The first time bind is called with the given id,
        //an openGL texture object is created and bound to the id
        //It also makes it the current texture.
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureID[0]);

        // Build texture initialised with image data.
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0,
                data.getInternalFormat(),
                data.getWidth(),
                data.getHeight(),
                0,
                data.getPixelFormat(),
                data.getPixelType(),
                data.getBuffer());

        setFilters(gl);
        textures.add(textureID[0]);
        return textureID[0];
    }

    private void setFilters(GL2 gl) {
        // Build the texture from data.
        if (Config.MIPMAP) {
            // Set texture parameters to enable automatic mipmap generation and bilinear/trilinear filtering
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);

            float fLargest[] = new float[1];
            gl.glGetFloatv(GL.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, fLargest, 0);
            gl.glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAX_ANISOTROPY_EXT, fLargest[0]);
            gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
        } else {
            // Set texture parameters to enable bilinear filtering.
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        }

    }

    /**
     * clean up memory
     *
     * @param gl the gl
     */
    public void dispose(GL2 gl) {
        IntBuffer buffer = Buffers.newDirectIntBuffer(ArrayUtils.getIntArrayFromList(vaos));
        gl.glDeleteVertexArrays(vaos.size(), buffer);
        buffer.clear();
        vaos.clear();

        buffer = Buffers.newDirectIntBuffer(ArrayUtils.getIntArrayFromList(vbos));
        gl.glDeleteBuffers(vbos.size(), buffer);
        buffer.clear();
        vbos.clear();
        ;

        buffer = Buffers.newDirectIntBuffer(ArrayUtils.getIntArrayFromList(textures));
        gl.glDeleteBuffers(textures.size(), buffer);
        buffer.clear();
        textures.clear();


    }

    /**
     * @return the VAO id just created
     */
    private int createVAO(GL2 gl) {
        IntBuffer buffer = IntBuffer.allocate(1);
        gl.glGenVertexArrays(1, buffer);
        int id = buffer.get(0);
        buffer.clear(); //clear buffer
        vaos.add(id);
        gl.glBindVertexArray(id);
        return id;
    }

    /**
     * create the VBO then store into the attributes list of current binding VAO
     *
     * @param gl             the gl,
     * @param attributeIndex the index of attributes list
     * @param data           the data
     * @param size           the size of each data
     */
    private void storeVBOArrayBuffer(GL2 gl, int attributeIndex, float[] data, int size) {
        IntBuffer buffer = IntBuffer.allocate(1);
        gl.glGenBuffers(1, buffer);
        int id = buffer.get(0);
        buffer.clear();
        vbos.add(id);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, id);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, data.length * MathUtils.sizeOf(Float.class), BufferUtils.copyBuffer(data), GL.GL_STATIC_DRAW);
        gl.glVertexAttribPointer(attributeIndex, size, GL2.GL_FLOAT, false, 0, 0);
        //unbind VBO
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
    }

    /**
     * unbind currentVAO, stupid method anyway.
     *
     * @param gl the gl
     */
    private void unbindVAO(GL2 gl) {
        gl.glBindVertexArray(0);
    }

    /**
     * @param gl      the gl
     * @param indices indies array need to put into VBO element array buffer
     */
    private void bindIndicesBuffer(GL2 gl, int[] indices) {
        IntBuffer buffer = IntBuffer.allocate(1);
        gl.glGenBuffers(1, buffer);
        int vboId = buffer.get(0);
        buffer.clear();

        vbos.add(vboId);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, vboId);
        gl.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, indices.length * MathUtils.sizeOf(Integer.class), BufferUtils.copyBuffer(indices), GL.GL_STATIC_DRAW);
    }


}
