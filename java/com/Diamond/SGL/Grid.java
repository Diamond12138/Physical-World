package com.Diamond.SGL;
import java.util.ArrayList;
import android.renderscript.Float3;
import android.opengl.GLES32;

public class Grid extends Sprite {
    public int VAO;
    public int vCount;

    public static ObjLoader createGrid(int width, int height, boolean texturePerRect) {
        float xUnit = 1.0f / width;
        float zUnit = 1.0f / height;

        float zero = 0;
        float one = 1;

        ArrayList<Float> vers = new ArrayList<Float>();
        for (int i = 0;i < height - 1;i++) {
            for (int j = 0;j < width - 1;j++) {
                //第一个三角形
                vers.add(j * xUnit);vers.add(zero);vers.add(i * zUnit);
                vers.add(j * xUnit);vers.add(zero);vers.add((i + 1) * zUnit);
                vers.add((j + 1) * xUnit);vers.add(zero);vers.add((i + 1) * zUnit);

                //第二个三角形
                vers.add((j + 1) * xUnit);vers.add(zero);vers.add((i + 1) * zUnit);
                vers.add((j + 1) * xUnit);vers.add(zero);vers.add(i * zUnit);
                vers.add(j * xUnit);vers.add(zero);vers.add(i * zUnit);
            }
        }

        float[] vertices = new float[vers.size()];
        for (int i = 0;i < vers.size();i += 3) {
            vertices[i + 0] = vers.get(i + 0) - 0.5f;
            vertices[i + 1] = vers.get(i + 1);
            vertices[i + 2] = vers.get(i + 2) - 0.5f;
        }

        float[] normals = cal_normal(vertices);

        ArrayList<Float> texs = new ArrayList<Float>();
        if (texturePerRect) {
            for (int i = 0;i < vertices.length;i += 18) {
                texs.add(zero);texs.add(zero);texs.add(zero);
                texs.add(zero);texs.add(one);texs.add(zero);
                texs.add(one);texs.add(one);texs.add(zero);

                texs.add(one);texs.add(one);texs.add(zero);
                texs.add(one);texs.add(zero);texs.add(zero);
                texs.add(zero);texs.add(zero);texs.add(zero);
            }
        } else {
        	for (int i = 0;i < vertices.length;i += 18) {
                texs.add(vertices[i + 0]);
                texs.add(vertices[i + 2]);
                texs.add(vertices[i + 1]);
            }
        }

        float[] texCoords = new float[texs.size()];
        for (int i = 0;i < texs.size();i++) {
            texCoords[i] = texs.get(i);
        }

        return new ObjLoader(vertices, normals, texCoords, null);
    }
    
    public static float[] cal_normal(float[] vertices) {
        ArrayList<Float> nors = new ArrayList<Float>();
        for (int i = 0;i < vertices.length;i += 18) {
            Float3 A = new Float3(vertices[i +  0], vertices[i +  1], vertices[i +  2]);
            Float3 B = new Float3(vertices[i +  3], vertices[i +  4], vertices[i +  4]);
            Float3 C = new Float3(vertices[i +  6], vertices[i +  7], vertices[i +  8]);

            Float3 D = new Float3(vertices[i +  9], vertices[i + 10], vertices[i + 11]);
            Float3 E = new Float3(vertices[i + 12], vertices[i + 13], vertices[i + 14]);
            Float3 F = new Float3(vertices[i + 15], vertices[i + 16], vertices[i + 17]);

            Float3 V1 = VectorUtil.calculateNormal(A, B, C);V1.y *= V1.y;
            Float3 V2 = VectorUtil.calculateNormal(B, C, A);V2.y *= V2.y;
            Float3 V3 = VectorUtil.calculateNormal(C, A, B);V3.y *= V3.y;

            Float3 V4 = VectorUtil.calculateNormal(D, E, F);V4.y *= V4.y;
            Float3 V5 = VectorUtil.calculateNormal(E, F, D);V5.y *= V5.y;
            Float3 V6 = VectorUtil.calculateNormal(F, D, E);V6.y *= V6.y;

            float x1 = V1.x + V2.x + V3.x;
            float y1 = V1.y + V2.y + V3.y;
            float z1 = V1.z + V2.z + V3.z;
            Float3 N1 = VectorUtil.normalize(new Float3(x1, y1, z1));

            float x2 = V4.x + V5.x + V6.x;
            float y2 = V4.y + V5.y + V6.y;
            float z2 = V4.z + V5.z + V3.z;
            Float3 N2 = VectorUtil.normalize(new Float3(x2, y2, z2));

            float x = N1.x + N2.x;
            float y = N1.y + N2.y;
            y *= y;
            float z = N1.z + N2.z;
            Float3 N = VectorUtil.normalize(new Float3(x, y, z));

            nors.add(N.x);nors.add(N.y);nors.add(N.z);
            nors.add(N.x);nors.add(N.y);nors.add(N.z);
            nors.add(N.x);nors.add(N.y);nors.add(N.z);

            nors.add(N.x);nors.add(N.y);nors.add(N.z);
            nors.add(N.x);nors.add(N.y);nors.add(N.z);
            nors.add(N.x);nors.add(N.y);nors.add(N.z);
        }

        float[] normals = new float[nors.size()];
        for (int i = 0;i < nors.size();i++) {
            normals[i] = nors.get(i);
        }
        
        return normals;
    }
    
    public Grid() {}
    public Grid(int width, int height, boolean texturePerRect) {
        ObjLoader data = createGrid(width, height, texturePerRect);

        VAO = BufferUtil.genVertexArray();
        GLES32.glBindVertexArray(VAO);
        int vbo1 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_position, 3, data.vertices, GLES32.GL_STATIC_DRAW);
        int vbo2 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_normal, 3, data.normals, GLES32.GL_STATIC_DRAW);
        int vbo3 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_texCoord, 3, data.texCoords, GLES32.GL_STATIC_DRAW);
        GLES32.glBindVertexArray(0);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, 0);
        BufferUtil.deleteBuffers(new int[]{vbo1,vbo2,vbo3});

        vCount = data.vertices.length / 3;
    }

    public Grid draw(Program program) {
        program.setUniform("u_model", getMatrixArray());
        GLES32.glBindVertexArray(VAO);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLES, 0, vCount);
        GLES32.glBindVertexArray(0);
        return this;
    }
}
