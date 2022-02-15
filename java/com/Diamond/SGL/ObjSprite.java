package com.Diamond.SGL;

import java.io.InputStream;
import android.opengl.GLES32;
import java.util.Random;

public class ObjSprite extends Sprite {
    public int VAO;
    public int vCount;
    
    public ObjSprite(int vao,int vcount) {
        VAO = vao;
        vCount = vcount;
    }
    public ObjSprite(InputStream is) {
        ObjLoader result = new ObjLoader(is);
        
        vCount = result.vertices.length / 3;
        
        VAO = BufferUtil.genVertexArray();
        GLES32.glBindVertexArray(VAO);
        int vbo1 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_position,3,result.vertices,GLES32.GL_STATIC_DRAW);
        int vbo2 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_normal,3,result.normals,GLES32.GL_STATIC_DRAW);
        int vbo3 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_texCoord,3,result.texCoords,GLES32.GL_STATIC_DRAW);
        GLES32.glBindVertexArray(0);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,0);
        BufferUtil.deleteBuffers(new int[]{vbo1,vbo2,vbo3});
    }
    public ObjSprite(ObjLoader loader) {
        vCount = loader.vertices.length / 3;
        
        VAO = BufferUtil.genVertexArray();
        GLES32.glBindVertexArray(VAO);
        int vbo1 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_position,3,loader.vertices,GLES32.GL_STATIC_DRAW);
        int vbo2 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_normal,3,loader.normals,GLES32.GL_STATIC_DRAW);
        int vbo3 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_texCoord,3,loader.texCoords,GLES32.GL_STATIC_DRAW);
        GLES32.glBindVertexArray(0);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,0);
        BufferUtil.deleteBuffers(new int[]{vbo1,vbo2,vbo3});
    }
    
    public ObjSprite draw(Program program) {
        program.setUniform("u_model",getMatrixArray());
        GLES32.glBindVertexArray(VAO);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLES,0,vCount);
        GLES32.glBindVertexArray(0);
        return this;
    }
}