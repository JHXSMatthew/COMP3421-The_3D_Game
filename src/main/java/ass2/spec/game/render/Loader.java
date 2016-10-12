package ass2.spec.game.render;

import ass2.spec.game.models.RawModel;
import ass2.spec.game.utils.ArrayUtils;
import ass2.spec.game.utils.BufferUtils;
import ass2.spec.game.utils.MathUtils;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 30/09/2016.
 * The loader class to load all model into VAO
 */
public class Loader {

    public final static int ATTRIBUTE_POSITION = 0;


    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();


    /**
     *
     * @param gl the gl
     * @param positions vertex array
     * @param indices indices array
     * @param texture texture "location" array
     * @return the model
     */
    public RawModel loadModel(GL2 gl,float[] positions,int[] indices,double[] texture){
        int vaoID = createVAO(gl);
        storeVBOArrayBuffer(gl,ATTRIBUTE_POSITION,positions,3);
        bindIndicesBuffer(gl,indices);
        unbindVAO(gl);
        return new RawModel(vaoID,indices.length);
    }

    /**
     *  clean up memory
     * @param gl the gl
     */
    public void dispose(GL2 gl){
        IntBuffer buffer = Buffers.newDirectIntBuffer(ArrayUtils.getIntArrayFromList(vaos));
        gl.glDeleteVertexArrays(vaos.size(),buffer);
        buffer.clear();
        vaos.clear();

        buffer = Buffers.newDirectIntBuffer(ArrayUtils.getIntArrayFromList(vbos));
        gl.glDeleteBuffers(vbos.size(),buffer);
        buffer.clear();
        vbos.clear();;
    }

    /**
     *
     * @return the VAO id just created
     */
    private int createVAO(GL2 gl){
        IntBuffer buffer = IntBuffer.allocate(1);
        gl.glGenVertexArrays(1,buffer);
        int id = buffer.get(0);
        buffer.clear(); //clear buffer
        vaos.add(id);
        gl.glBindVertexArray(id);
        return id;
    }

    /**
     *  create the VBO then store into the attributes list of current binding VAO
     * @param gl the gl,
     * @param attributeIndex the index of attributes list
     * @param data the data
     * @param size the size of each data
     */
    private void storeVBOArrayBuffer(GL2 gl, int attributeIndex, float[] data , int size){
        IntBuffer buffer = IntBuffer.allocate(1);
        gl.glGenBuffers(1,buffer);
        int id = buffer.get(0);
        buffer.clear();
        vbos.add(id);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER,id);
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, data.length * MathUtils.sizeOf(Float.class),BufferUtils.copyBuffer(data), GL.GL_STATIC_DRAW);
        gl.glVertexAttribPointer(attributeIndex,size,GL2.GL_FLOAT,false,0,0);
        //unbind VBO
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER,0);
    }

    /**
     *  unbind currentVAO, stupid method anyway.
     * @param gl the gl
     */
    private void unbindVAO(GL2 gl){
        gl.glBindVertexArray(0);
    }

    /**
     *
     * @param gl the gl
     * @param indices indies array need to put into VBO element array buffer
     */
    private void bindIndicesBuffer(GL2 gl,int[] indices){
        IntBuffer buffer = IntBuffer.allocate(1);
        gl.glGenBuffers(1,buffer);
        int vboId = buffer.get(0);
        buffer.clear();

        vbos.add(vboId);
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER,vboId);
        gl.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, indices.length * MathUtils.sizeOf(Integer.class) , BufferUtils.copyBuffer(indices), GL.GL_STATIC_DRAW);
    }




}
