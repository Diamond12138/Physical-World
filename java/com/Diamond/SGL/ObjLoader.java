package com.Diamond.SGL;

import java.io.InputStream;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import android.util.Log;

public class ObjLoader {
    public float[] vertices = null;
    public float[] normals = null;
    public float[] texCoords = null;
    public int[] indices = null;

    public ObjLoader() {}
    public ObjLoader(InputStream is) {
        ObjLoader result = ObjLoader.loadFromStream(is);
        vertices = result.vertices;
        normals = result.normals;
        texCoords = result.texCoords;
        indices = result.indices;
    }
    public ObjLoader(float[] v, float[] n, float[] t, int[] i) {
        vertices = v;
        normals = n;
        texCoords = t;
        indices = i;
    }

    public static ObjLoader loadFromStreamOnlyVerticesAndIndices(InputStream is) {
        ArrayList<Float> vertexArray = new ArrayList<Float>();
        ArrayList<Integer> indexArray = new ArrayList<Integer>();

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String temp = null;

        try {
            while ((temp = br.readLine()) != null) {
                String[] templist = temp.split("[ ]+");
                if (templist[0].trim().equals("v")) {
                    vertexArray.add(Float.parseFloat(templist[1]));
                    vertexArray.add(Float.parseFloat(templist[2]));
                    vertexArray.add(Float.parseFloat(templist[3]));
                } else if (templist[0].trim().equals("f")) {
                    String[] tempf1 = templist[1].split("[/]+");
                    String[] tempf2 = templist[2].split("[/]+");
                    String[] tempf3 = templist[3].split("[/]+");
                    indexArray.add(Integer.parseInt(tempf1[0]) - 1);
                    indexArray.add(Integer.parseInt(tempf2[0]) - 1);
                    indexArray.add(Integer.parseInt(tempf3[0]) - 1);
                }
            }
        } catch (IOException e) {
            Log.e("ObjLoader", "");
            e.printStackTrace();
        }

        float[] vertices = new float[vertexArray.size()];
        for (int i = 0;i < vertexArray.size();i++) {
            vertices[i] = vertexArray.get(i);
        }
        int[] indices = new int[indexArray.size()];
        for (int i = 0;i < indexArray.size();i++) {
            indices[i] = indexArray.get(i);
        }

        ObjLoader result = new ObjLoader(vertices, null, null, indices);

        return result;
    }

    public static ObjLoader loadFromStream(InputStream is) {
        ArrayList<Float> vertexArray = new ArrayList<Float>();
        ArrayList<Float> normalArray = new ArrayList<Float>();
        ArrayList<Float> texCoordArray = new ArrayList<Float>();

        ArrayList<Float> vertexArrayResult = new ArrayList<Float>();
        ArrayList<Float> normalArrayResult = new ArrayList<Float>();
        ArrayList<Float> texCoordArrayResult = new ArrayList<Float>();

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String temp = null;

        try {
            while ((temp = br.readLine()) != null) {
                String[] templist = temp.split("[ ]+");
                if (templist[0].trim().equals("v")) {
                    vertexArray.add(Float.parseFloat(templist[1]));
                    vertexArray.add(Float.parseFloat(templist[2]));
                    vertexArray.add(Float.parseFloat(templist[3]));
                } else if (templist[0].trim().equals("vn")) {
                	normalArray.add(Float.parseFloat(templist[1]));
                    normalArray.add(Float.parseFloat(templist[2]));
                    normalArray.add(Float.parseFloat(templist[3]));
                } else if (templist[0].trim().equals("vt")) {
                	texCoordArray.add(Float.parseFloat(templist[1]));
                    texCoordArray.add(Float.parseFloat(templist[2]));
                    texCoordArray.add(Float.parseFloat(templist[3]));
                } else if (templist[0].trim().equals("f")) {
                    String[][] temps = new String[templist.length - 1][];
                    for (int i = 0;i < temps.length;i++) {
                        temps[i] = templist[i + 1].split("[/]+");
                    }
                    boolean ibt3 = temps.length > 3; //is bigger than 3，像极了把int打成ibt

                    int index = Integer.parseInt(temps[0][0]) - 1;
                    vertexArrayResult.add(vertexArray.get(3 * index));
                    vertexArrayResult.add(vertexArray.get(3 * index + 1));
                    vertexArrayResult.add(vertexArray.get(3 * index + 2));
                    index = Integer.parseInt(temps[1][0]) - 1;
                    vertexArrayResult.add(vertexArray.get(3 * index));
                    vertexArrayResult.add(vertexArray.get(3 * index + 1));
                    vertexArrayResult.add(vertexArray.get(3 * index + 2));
                    index = Integer.parseInt(temps[2][0]) - 1;
                    vertexArrayResult.add(vertexArray.get(3 * index));
                    vertexArrayResult.add(vertexArray.get(3 * index + 1));
                    vertexArrayResult.add(vertexArray.get(3 * index + 2));

                    if (ibt3) {
                        index = Integer.parseInt(temps[0][0]) - 1;
                        vertexArrayResult.add(vertexArray.get(3 * index));
                        vertexArrayResult.add(vertexArray.get(3 * index + 1));
                        vertexArrayResult.add(vertexArray.get(3 * index + 2));
                        index = Integer.parseInt(temps[2][0]) - 1;
                        vertexArrayResult.add(vertexArray.get(3 * index));
                        vertexArrayResult.add(vertexArray.get(3 * index + 1));
                        vertexArrayResult.add(vertexArray.get(3 * index + 2));
                        index = Integer.parseInt(temps[3][0]) - 1;
                        vertexArrayResult.add(vertexArray.get(3 * index));
                        vertexArrayResult.add(vertexArray.get(3 * index + 1));
                        vertexArrayResult.add(vertexArray.get(3 * index + 2));
                    }

                    if (texCoordArray.size() != 0) {
                        index = Integer.parseInt(temps[0][1]) - 1;
                        texCoordArrayResult.add(texCoordArray.get(3 * index));
                        texCoordArrayResult.add(texCoordArray.get(3 * index + 1));
                        texCoordArrayResult.add(texCoordArray.get(3 * index + 2));
                        index = Integer.parseInt(temps[1][1]) - 1;
                        texCoordArrayResult.add(texCoordArray.get(3 * index));
                        texCoordArrayResult.add(texCoordArray.get(3 * index + 1));
                        texCoordArrayResult.add(texCoordArray.get(3 * index + 2));
                        index = Integer.parseInt(temps[2][1]) - 1;
                        texCoordArrayResult.add(texCoordArray.get(3 * index));
                        texCoordArrayResult.add(texCoordArray.get(3 * index + 1));
                        texCoordArrayResult.add(texCoordArray.get(3 * index + 2));

                        if (ibt3) {
                            index = Integer.parseInt(temps[0][1]) - 1;
                            texCoordArrayResult.add(texCoordArray.get(3 * index));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 1));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 2));
                            index = Integer.parseInt(temps[2][1]) - 1;
                            texCoordArrayResult.add(texCoordArray.get(3 * index));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 1));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 2));
                            index = Integer.parseInt(temps[3][1]) - 1;
                            texCoordArrayResult.add(texCoordArray.get(3 * index));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 1));
                            texCoordArrayResult.add(texCoordArray.get(3 * index + 2));
                        }
                    }

                    if (normalArray.size() != 0) {
                        index = Integer.parseInt(temps[0][2]) - 1;
                        normalArrayResult.add(normalArray.get(3 * index));
                        normalArrayResult.add(normalArray.get(3 * index + 1));
                        normalArrayResult.add(normalArray.get(3 * index + 2));
                        index = Integer.parseInt(temps[1][2]) - 1;
                        normalArrayResult.add(normalArray.get(3 * index));
                        normalArrayResult.add(normalArray.get(3 * index + 1));
                        normalArrayResult.add(normalArray.get(3 * index + 2));
                        index = Integer.parseInt(temps[2][2]) - 1;
                        normalArrayResult.add(normalArray.get(3 * index));
                        normalArrayResult.add(normalArray.get(3 * index + 1));
                        normalArrayResult.add(normalArray.get(3 * index + 2));

                        if (ibt3) {
                            index = Integer.parseInt(temps[0][2]) - 1;
                            normalArrayResult.add(normalArray.get(3 * index));
                            normalArrayResult.add(normalArray.get(3 * index + 1));
                            normalArrayResult.add(normalArray.get(3 * index + 2));
                            index = Integer.parseInt(temps[2][2]) - 1;
                            normalArrayResult.add(normalArray.get(3 * index));
                            normalArrayResult.add(normalArray.get(3 * index + 1));
                            normalArrayResult.add(normalArray.get(3 * index + 2));
                            index = Integer.parseInt(temps[3][2]) - 1;
                            normalArrayResult.add(normalArray.get(3 * index));
                            normalArrayResult.add(normalArray.get(3 * index + 1));
                            normalArrayResult.add(normalArray.get(3 * index + 2));
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("ObjLoader", "");
            e.printStackTrace();
        }

        float[] vertices = new float[vertexArrayResult.size()];
        for (int i = 0;i < vertexArrayResult.size();i++) {
            vertices[i] = vertexArrayResult.get(i);
        }
        float[] normals = new float[normalArrayResult.size()];
        for (int i = 0;i < normalArrayResult.size();i++) {
            normals[i] = normalArrayResult.get(i);
        }
        float[] texCoords = new float[texCoordArrayResult.size()];
        for (int i = 0;i < texCoordArrayResult.size();i++) {
            texCoords[i] = texCoordArrayResult.get(i);
        }

        ObjLoader result = new ObjLoader(vertices, normals, texCoords, null);

        return result;
    }
}
