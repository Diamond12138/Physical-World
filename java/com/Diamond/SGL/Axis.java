package com.Diamond.SGL;

import android.opengl.GLES32;

public class Axis extends Sprite {
    public int VAO;
    
    public Axis() {
        float[] vertices = new float[]{
            0,0,0,
            100,0,0,
            
            0,0,0,
            0,100,0,
            
            0,0,0,
            0,0,100
        };
        float[] colors = new float[]{
            1,0,0,1,
            1,0,0,1,
            
            0,1,0,1,
            0,1,0,1,
            
            0,0,1,1,
            0,0,1,1
        };
        
        VAO = BufferUtil.genVertexArray();
        GLES32.glBindVertexArray(VAO);
        int vbo1 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_position,3,vertices,GLES32.GL_STATIC_DRAW);
        int vbo2 = BufferUtil.bindVBO(Program.VertexAttribLocation.a_color,4,colors,GLES32.GL_STATIC_DRAW);
        GLES32.glBindVertexArray(0);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,0);
        BufferUtil.deleteBuffers(new int[]{vbo1,vbo2});
    }
    
    public Axis draw(Program program) {
        program.setUniform("u_model",getMatrixArray());
        GLES32.glBindVertexArray(VAO);
        GLES32.glDrawArrays(GLES32.GL_LINES,0,6);
        GLES32.glBindVertexArray(0);
        return this;
    }
}
