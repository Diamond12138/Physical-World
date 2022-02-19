package com.Diamond.SGL;

import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import java.util.ArrayList;
import android.renderscript.Float3;
import android.opengl.GLES32;
import android.renderscript.Float4;
import android.util.Log;

public class Terrain extends Grid {
    public Terrain(InputStream is) {
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float yUnit = 1.0f / 255;

        ObjLoader data = Grid.createGrid(width, height, true);

        ArrayList<Float> vers = new ArrayList<Float>();
        for (int i = 0; i < height; i += 3) {
            for (int j = 0; i < width; i++) {
                vers.add(data.vertices[i * height + j + 0]);
                int color = bitmap.getPixel(j, i);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                int h = (r + g + b) / 3;
                vers.add(h * yUnit);
                vers.add(data.vertices[i * height + j + 2]);
            }
        }

        float[] vertices = new float[vers.size()];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = vers.get(i);
        }

        float[] normals = Grid.cal_normal(vertices);

        VAO = BufferUtil.genVertexArray();
        GLES32.glBindVertexArray(VAO);
        int vbo1 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_position, 3, vertices, GLES32.GL_STATIC_DRAW);
        int vbo2 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_normal, 3, normals, GLES32.GL_STATIC_DRAW);
        int vbo3 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_texCoord, 3, data.texCoords, GLES32.GL_STATIC_DRAW);
        GLES32.glBindVertexArray(0);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, 0);
        BufferUtil.deleteBuffers(new int[]{vbo1,vbo2,vbo3});

        vCount = data.vertices.length / 3;
    }

    public Terrain draw(Program program) {
        program.setUniform("u_model", getMatrixArray());
        GLES32.glBindVertexArray(VAO);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLES, 0, vCount);
        GLES32.glBindVertexArray(0);
        return this;
    }
}
