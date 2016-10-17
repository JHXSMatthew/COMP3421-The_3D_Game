package ass2.spec.game.Shaders;

import com.jogamp.opengl.GL2;

/**
 * Created by matthew on 16/10/16.
 */
public abstract class ShaderProgram {


    private int programID;
    private int vertexShaderID;
    private int fragmentShaerID;

    public ShaderProgram(String vertexShader, String ){

    }

    public static int initShaders(GL2 gl, String vs, String fs) throws Exception {
        Shader vertexShader = new Shader(GL2.GL_VERTEX_SHADER, new File(vs));
        vertexShader.compile(gl);
        Shader fragmentShader = new Shader(GL2.GL_FRAGMENT_SHADER, new File(fs));
        fragmentShader.compile(gl);

        //Each shaderProgram must have
        //one vertex shader and one fragment shader.
        int shaderprogram = gl.glCreateProgram();
        gl.glAttachShader(shaderprogram, vertexShader.getID());
        gl.glAttachShader(shaderprogram, fragmentShader.getID());

        gl.glLinkProgram(shaderprogram);


        int[] error = new int[2];
        gl.glGetProgramiv(shaderprogram, GL2.GL_LINK_STATUS, error, 0);
        if (error[0] != GL2.GL_TRUE) {
            int[] logLength = new int[1];
            gl.glGetProgramiv(shaderprogram, GL2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetProgramInfoLog(shaderprogram, logLength[0], (int[]) null, 0, log, 0);

            System.out.printf("Failed to link shader! %s\n", new String(log));
            throw new CompilationException("Error linking the shader: "
                    + new String(log));
        }

        gl.glValidateProgram(shaderprogram);

        gl.glGetProgramiv(shaderprogram, GL2.GL_VALIDATE_STATUS, error, 0);
        if (error[0] != GL2.GL_TRUE) {
            System.out.printf("Failed to validate shader!\n");
            throw new Exception("program failed to validate");
        }


        return shaderprogram;
    }

    static public class CompilationException extends RuntimeException {

        public CompilationException(String message) {
            super(message);
        }

    }
}
