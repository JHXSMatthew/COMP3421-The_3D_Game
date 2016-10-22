package game.shaders;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.math.Matrix4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

/**
 * Created by matthew on 16/10/16.
 */
public abstract class BasicShader {


    private static FloatBuffer matrixBuffer = Buffers.newDirectFloatBuffer(16);
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;


    /**
     * wrapper of OpenGL shader program
     *
     * @param vertexShader   vertex shader
     * @param fragmentShader fragment shader
     */
    public BasicShader(GL2 gl, String vertexShader, String fragmentShader) {
        vertexShaderID = loadShader(gl, vertexShader, GL2.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(gl, fragmentShader, GL2.GL_FRAGMENT_SHADER);
        programID = gl.glCreateProgram();
        gl.glAttachShader(programID, vertexShaderID);
        gl.glAttachShader(programID, fragmentShaderID);
        gl.glLinkProgram(programID);
        bindAttributes(gl);
        gl.glValidateProgram(programID);
        getAllUniformLocations(gl);

    }

    /**
     *
     * @param gl gl
     * @param file shader file
     * @param type shader type, fragment or vertex
     * @return
     */
    public static int loadShader(GL2 gl, String file, int type) {
        StringBuilder source = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                source.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Shader program error!");
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderID = gl.glCreateShader(type);
        String[] dump = new String[1];
        dump[0] = source.toString();
        gl.glShaderSource(shaderID, 1, dump, new int[]{dump[0].length()}, 0);
        gl.glCompileShader(shaderID);
        int[] error = new int[2];
        gl.glGetProgramiv(shaderID, GL2.GL_LINK_STATUS, error, 0);
        int[] compiled = new int[1];
        gl.glGetShaderiv(shaderID, GL2.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            int[] logLength = new int[1];
            gl.glGetShaderiv(shaderID, GL2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetShaderInfoLog(shaderID, logLength[0], (int[]) null, 0, log, 0);

            System.err.println("Error compiling the shader: "
                    + new String(log));
        }
        if (error[0] != GL2.GL_FALSE) {
            int[] logLength = new int[1];
            gl.glGetProgramiv(shaderID, GL2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetProgramInfoLog(shaderID, logLength[0], (int[]) null, 0, log, 0);

            System.out.printf("Failed to link shader! %s\n", new String(log));
        }

        return shaderID;
    }

    /**
     *
     * @param gl gl
     */
    public void start(GL2 gl) {
        gl.glUseProgram(this.programID);
    }

    /**
     *
     * @param gl gl
     */
    public void stop(GL2 gl) {
        gl.glUseProgram(0);
    }

    /**
     *
     * @param gl gl
     */
    public void dispose(GL2 gl) {
        stop(gl);
        gl.glDetachShader(programID, vertexShaderID);
        gl.glDetachShader(programID, fragmentShaderID);
        gl.glDeleteShader(vertexShaderID);
        gl.glDeleteShader(fragmentShaderID);
        gl.glDeleteProgram(programID);
    }

    /**
     *
     * @param gl2 gl
     * @param var the attribute name
     * @param attribute the attribute index
     */
    protected void bindAttribute(GL2 gl2, String var, int attribute) {
        gl2.glBindAttribLocation(programID, attribute, var);
    }

    /**
     *  load attribute to shader
     * @param gl gl
     * @param location location
     * @param value value
     */
    protected void loadFloat(GL2 gl, int location, float value) {
        gl.glUniform1f(location, value);
    }

    /**
     *  load attribute to shader
     * @param gl gl
     * @param location location
     * @param value value
     */
    protected void loadVector(GL2 gl, int location, float[] value) {
        gl.glUniform3f(location, value[0], value[1], value[2]);
    }

    /**
     *  load attribute to shader
     * @param gl gl
     * @param location location
     * @param value value
     */
    protected void loadMatrix(GL2 gl, int location, Matrix4 value) {
        matrixBuffer.put(value.getMatrix());
        matrixBuffer.flip();
        gl.glUniformMatrix4fv(location, 1, false, matrixBuffer);
    }


    protected abstract void bindAttributes(GL2 gl);

    protected abstract void getAllUniformLocations(GL2 gl);

    /**
     *
     * @param gl gl
     * @param uniformName the uniform name
     * @return the uniform location (number) in shader
     */
    protected int getUniformLocation(GL2 gl, String uniformName) {
        return gl.glGetUniformLocation(programID, uniformName);
    }

}
