package game.utils;

import com.jogamp.opengl.GL2;
import game.models.RawModel;
import game.render.Loader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 20/10/2016.
 */
public class OBJUtils {


    public static RawModel loadRawModel(GL2 gl, String path, Loader loader) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        //buffer
        List<Float> vertices = new ArrayList<>();
        List<Float> textures = new ArrayList<>();
        List<Float> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        //actuall data

        float[] verticesArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;


        boolean isFirst = true;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.equals("") || line.startsWith("#")) {
                    continue;
                }
                String[] current = line.split(" ");
                if (line.startsWith("v ")) {
                    vertices.add(Float.parseFloat(current[1]));
                    vertices.add(Float.parseFloat(current[2]));
                    vertices.add(Float.parseFloat(current[3]));

                } else if (line.startsWith("vt ")) {
                    textures.add(Float.parseFloat(current[1]));
                    textures.add(Float.parseFloat(current[2]));
                } else if (line.startsWith("vn ")) {
                    normals.add(Float.parseFloat(current[1]));
                    normals.add(Float.parseFloat(current[2]));
                    normals.add(Float.parseFloat(current[3]));
                } else if (line.startsWith("f ")) {
                    if (isFirst) {
                        normalsArray = new float[vertices.size()];
                        textureArray = new float[2 * vertices.size() / 3];
                        isFirst = false;
                        //System.out.println("v:" + vertices.size() + " n:" + normals.size() + " t:" + textures.size());
                    }
                    String[] vertex1 = current[1].split("/");
                    String[] vertex2 = current[2].split("/");
                    String[] vertex3 = current[3].split("/");

                    processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                    processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                    processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        verticesArray = ArrayUtils.getFloatArrayFromList(vertices);

        return loader.loadToVAO(gl, verticesArray, ArrayUtils.getIntArrayFromList(indices), textureArray, normalsArray);

    }

    /**
     * load each mapping
     * vertexData ----> indices|textures|normals|
     *
     * @param vertexData
     * @param indices
     * @param textures
     * @param normals
     * @param textureArray
     * @param normalsArray
     */
    private static void processVertex(String[] vertexData, List<Integer> indices, List<Float> textures,
                                      List<Float> normals, float[] textureArray, float[] normalsArray) {
        int pointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(pointer);
        textureArray[pointer * 2] = textures.get(2 * (Integer.parseInt(vertexData[1]) - 1));
        textureArray[pointer * 2 + 1] = textures.get(2 * (Integer.parseInt(vertexData[1]) - 1) + 1);
        normalsArray[pointer * 3] = normals.get(3 * (Integer.parseInt(vertexData[2]) - 1));
        normalsArray[pointer * 3 + 1] = normals.get(3 * (Integer.parseInt(vertexData[2]) - 1) + 1);
        normalsArray[pointer * 3 + 2] = normals.get(3 * (Integer.parseInt(vertexData[2]) - 1) + 2);
    }
}
