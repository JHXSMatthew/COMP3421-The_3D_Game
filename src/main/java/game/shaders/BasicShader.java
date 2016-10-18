package game.shaders;

import com.jogamp.opengl.GL2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by matthew on 16/10/16.
 */
public abstract class BasicShader {


    private int programID;
    private int vertexShaderID;
    private int fragmentShaerID;

    /**
     *  wrapper of OpenGL shader program
     * @param vertexShader vertex shader
     * @param fragmentShader fragment shader
     */
    public BasicShader(GL2 gl, String vertexShader, String fragmentShader){
        vertexShaderID = loadShader(gl,vertexShader,GL2.GL_VERTEX_SHADER);
        fragmentShaerID = loadShader(gl,fragmentShader,GL2.GL_FRAGMENT_SHADER);
        programID = gl.glCreateProgram();
        gl.glAttachShader(programID,vertexShaderID);
        gl.glAttachShader(programID,fragmentShaerID);
        gl.glLinkProgram(programID);
        bindAttributes(gl);
        gl.glValidateProgram(programID);
    }

    public void start(GL2 gl){
        gl.glUseProgram(this.programID);
    }

    public void stop(GL2 gl){
        gl.glUseProgram(0);
    }

    public void dispose(GL2 gl){
        stop(gl);
        gl.glDetachShader(programID,vertexShaderID);
        gl.glDetachShader(programID,fragmentShaerID);
        gl.glDeleteShader(vertexShaderID);
        gl.glDeleteShader(fragmentShaerID);
        gl.glDeleteProgram(programID);
    }


    protected void bindAttribute(GL2 gl2, String var, int attribute ){
        gl2.glBindAttribLocation(programID,attribute,var);
    }

    protected abstract void bindAttributes(GL2 gl);

    public static int loadShader(GL2 gl, String file, int type)  {
        StringBuilder source = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null){
                source.append(line).append("\n");
            }
            reader.close();
        }catch (IOException e){
            System.err.println("Shader program error!");
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderID = gl.glCreateShader(type);
        String[] dump = new String[1];
        dump[0] = source.toString();
        gl.glShaderSource(shaderID,1,dump,new int[] { dump[0].length() },0);
        gl.glCompileShader(shaderID);
        int[] error = new int[2];
        gl.glGetProgramiv(shaderID, GL2.GL_LINK_STATUS, error, 0);
        if(error[0] != GL2.GL_FALSE){
            int[] logLength = new int[1];
            gl.glGetProgramiv(shaderID, GL2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetProgramInfoLog(shaderID, logLength[0], (int[]) null, 0, log, 0);

            System.out.printf("Failed to link shader! %s\n", new String(log));
        }

        return shaderID;
    }

}
