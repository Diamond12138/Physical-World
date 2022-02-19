package com.Diamond.SGL;

import android.opengl.GLES32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.Buffer;

public class BufferUtil {
    public static ByteBuffer newByteBuffer(int capacity) {
        ByteBuffer bb = ByteBuffer.allocateDirect(capacity);
        bb.order(ByteOrder.nativeOrder());
        return bb;
    }
    public static FloatBuffer toFloatBuffer(float[] array) {
        FloatBuffer fb = newByteBuffer(array.length * 4).asFloatBuffer();
        fb.put(array).position(0);
        return fb;
    }
    public static IntBuffer toIntBuffer(int[] array) {
        IntBuffer ib = newByteBuffer(array.length * 4).asIntBuffer();
        ib.put(array).position(0);
        return ib;
    }



    public static void enableVertexAttrib(int location, int vertex_size) {
        GLES32.glEnableVertexAttribArray(location);
        GLES32.glVertexAttribPointer(location, vertex_size, GLES32.GL_FLOAT, false, vertex_size * 4, 0);
    }
    public static void bindBufferData(int buffer, int type, int data_size, Buffer data, int usage) {
        GLES32.glBindBuffer(type, buffer);
        GLES32.glBufferData(type, data_size, data, usage);
    }



    public static int genBuffer() {
        int[] buffer = new int[1];
        GLES32.glGenBuffers(1, buffer, 0);
        return buffer[0];
    }
    public static int[] genBuffers(int number) {
        number = Math.max(number, 1);
        int[] buffers = new int[number];
        GLES32.glGenBuffers(number, buffers, 0);
        return buffers;
    }
    public static void deleteBuffer(int buffer) {
        if (buffer != 0) {
            GLES32.glDeleteBuffers(1, new int[]{ buffer }, 0);
        }
    }
    public static void deleteBuffers(int[] buffers) {
        GLES32.glDeleteBuffers(buffers.length, buffers, 0);
    }



    public static int genVertexArray() {
        int[] buffer = new int[1];
        GLES32.glGenVertexArrays(1, buffer, 0);
        return buffer[0];
    }
    public static int[] genVertexArray(int number) {
        number = Math.max(number, 1);
        int[] buffers = new int[number];
        GLES32.glGenVertexArrays(number, buffers, 0);
        return buffers;
    }
    public static void deleteVertexArray(int buffer) {
        if (buffer != 0) {
            GLES32.glDeleteVertexArrays(1, new int[]{ buffer }, 0);
        }
    }
    public static void deleteVertexArray(int[] buffers) {
        GLES32.glDeleteVertexArrays(buffers.length, buffers, 0);
    }



    public static int bindVBO(int location, int vertex_size, float[] vertices, int usage) {
        int VBO = genBuffer();
        bindBufferData(VBO, GLES32.GL_ARRAY_BUFFER, vertices.length * 4, toFloatBuffer(vertices), usage);
        enableVertexAttrib(location, vertex_size);
        return VBO;
    }
    public static int bindEBO(int[] indices, int usage) {
        int EBO = genBuffer();
        bindBufferData(EBO, GLES32.GL_ELEMENT_ARRAY_BUFFER, indices.length * 4, toIntBuffer(indices), usage);
        return EBO;
    }
    
    
    
    public static boolean bindMapBuffer(int type,int offset,int data_size,Buffer data,int access) {
        ByteBuffer bb = (ByteBuffer)GLES32.glMapBufferRange(type,offset,data_size,access);
        if(bb == null) {
            return false;
        }
        bb.order(ByteOrder.nativeOrder());
        bb = (ByteBuffer)data;
        bb.position(0);
        return GLES32.glUnmapBuffer(type);
    }
    public static boolean bindMapBuffer(int type,int offset,float[] array,int access) {
        return bindMapBuffer(type,offset,array.length * 4,toFloatBuffer(array),access);
    }
    public static boolean bindMapBuffer(int type,int offset,int[] array,int access) {
        return bindMapBuffer(type,offset,array.length * 4,toIntBuffer(array),access);
    }
    
    public static boolean bindVBOMap(int offset,float[] array,int access) {
        return bindMapBuffer(GLES32.GL_ARRAY_BUFFER,offset,array,access);
    }
    public static boolean bindEBOMap(int offset,int[] array,int access) {
        return bindMapBuffer(GLES32.GL_ELEMENT_ARRAY_BUFFER,offset,array,access);
    }
}
