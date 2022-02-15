package com.Diamond.SGL;

import java.io.InputStream;
import android.opengl.GLES32;
import android.util.Log;

public class ObjInstance extends Instance {
	public int[] VBOs;
	
    public ObjInstance(ObjLoader loader,int max) {
        super(loader.vertices.length / 3);
        
        VBOs = new int[4];
        
        float[] matrices = new float[max * 16];
        for (int i = 0; i < matrices.length; i++) {
            matrices[i] = 0;
        }
        
        VBOs[0] = BufferUtil.bindVBO(Program.VertexAttribLocation.a_position, 3, loader.vertices, GLES32.GL_STATIC_DRAW);
        VBOs[1] = BufferUtil.bindVBO(Program.VertexAttribLocation.a_normal, 3, loader.normals, GLES32.GL_STATIC_DRAW);
        VBOs[2] = BufferUtil.bindVBO(Program.VertexAttribLocation.a_texCoord, 3, loader.texCoords, GLES32.GL_STATIC_DRAW);
        VBOs[3] = BufferUtil.genBuffer();
        BufferUtil.bindBufferData(VBOs[3],GLES32.GL_ARRAY_BUFFER,matrices.length * 4,BufferUtil.toFloatBuffer(matrices),GLES32.GL_DYNAMIC_DRAW);
        GLES32.glVertexAttribPointer(4,4,GLES32.GL_FLOAT,false,16 * 4,16 * 0);
        GLES32.glEnableVertexAttribArray(4);
        GLES32.glVertexAttribDivisor(4,1);
        GLES32.glVertexAttribPointer(5,4,GLES32.GL_FLOAT,false,16 * 4,16 * 1);
        GLES32.glEnableVertexAttribArray(5);
        GLES32.glVertexAttribDivisor(5,1);
        GLES32.glVertexAttribPointer(6,4,GLES32.GL_FLOAT,false,16 * 4,16 * 2);
        GLES32.glEnableVertexAttribArray(6);
        GLES32.glVertexAttribDivisor(6,1);
        GLES32.glVertexAttribPointer(7,4,GLES32.GL_FLOAT,false,16 * 4,16 * 3);
        GLES32.glEnableVertexAttribArray(7);
        GLES32.glVertexAttribDivisor(7,1);
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,0);
    }
    
    public float[] getMatrices() {
        float[] matrices = new float[sprites.size() * 16];
        for (int i = 0; i < sprites.size(); i++) {
            float[] matrix = sprites.get(i).getMatrixArray();
            for (int j = 0; j < 16; j++) {
                matrices[i * 16 + j] = matrix[j];
            }
        }
        return matrices;
    }
    
    public ObjInstance update() {
        float[] matrices = getMatrices();
        
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,VBOs[3]);
        GLES32.glBufferSubData(GLES32.GL_ARRAY_BUFFER,0,matrices.length * 4,BufferUtil.toFloatBuffer(matrices));
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,0);
        return this;
    }
    
    public ObjInstance delete() {
        BufferUtil.deleteBuffers(VBOs);
        return this;
    }
    
    @Override
    public ObjInstance draw(Program program) {
    	for (int i = 0; i < VBOs.length; i++) {
            GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,VBOs[i]);
        }
        GLES32.glDrawArraysInstanced(GLES32.GL_TRIANGLES,0,vCount,sprites.size());
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,0);
        return this;
    }
}
