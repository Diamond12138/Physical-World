package com.Diamond.SGL;

import android.opengl.GLES32;
import android.content.res.Resources;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import android.renderscript.Float2;
import android.renderscript.Float3;
import android.renderscript.Float4;
import android.util.Log;

public class Program {
    private int mProgramID;
    
    public class VertexAttribLocation {
        public static final int a_position = 0;
        public static final int a_color = 1;
        public static final int a_normal = 2;
        public static final int a_texCoord = 3;
    }

    public Program(int programID) {
        mProgramID = programID;
    }
    public Program(int vertexShader, int fragmentShader, int geometryShader) {
        mProgramID = linkProgram(vertexShader, fragmentShader, geometryShader);
    }
    public Program(String vertexResources, String fragmentResources, String geometryResources) {
        int vertexShader = loadShader(GLES32.GL_VERTEX_SHADER, vertexResources);
        int fragmentShader = loadShader(GLES32.GL_FRAGMENT_SHADER, fragmentResources);
        int geometryShader = 0;
        if (geometryResources != null) {
            geometryShader = loadShader(GLES32.GL_GEOMETRY_SHADER, geometryResources);
        }
        mProgramID = linkProgram(vertexShader, fragmentShader, geometryShader);
    }
    public Program(String vertexPath, String fragmentPath, String geometryPath, Resources resources) {
        String vertexResources = loadFromAssetsFile(vertexPath, resources);
        String fragmentResources = loadFromAssetsFile(fragmentPath, resources);
        String geometryResources = null;
        if (geometryPath != null) {
            loadFromAssetsFile(geometryPath, resources);
        }
        int vertexShader = loadShader(GLES32.GL_VERTEX_SHADER, vertexResources);
        int fragmentShader = loadShader(GLES32.GL_FRAGMENT_SHADER, fragmentResources);
        int geometryShader = 0;
        if (geometryResources != null) {
            geometryShader = loadShader(GLES32.GL_GEOMETRY_SHADER, geometryResources);
        }
        mProgramID = linkProgram(vertexShader, fragmentShader, geometryShader);
    }



    public int getProgramID() {
        return mProgramID;
    }
    public Program setProgram(int programID) {
        if (programID != mProgramID) {
            if (mProgramID != 0) {
                GLES32.glDeleteProgram(mProgramID);
            }
            mProgramID = programID;
        }
        return this;
    }

    public Program useProgram() {
        GLES32.glUseProgram(mProgramID);
        return this;
    }
    
    public int getUniformLocation(String name) {
        return GLES32.glGetUniformLocation(mProgramID,name);
    }
    
    public Program setUniform(String name,float value) {
        int location = getUniformLocation(name);
        GLES32.glUniform1f(location,value);
        return this;
    }
    public Program setUniform(String name,Float2 value) {
        return setUniform(name,value.x,value.y);
    }
    public Program setUniform(String name,float value1,float value2) {
        int location = getUniformLocation(name);
        GLES32.glUniform2f(location,value1,value2);
        return this;
    }
    public Program setUniform(String name,Float3 value) {
        return setUniform(name,value.x,value.y,value.z);
    }
    public Program setUniform(String name,float value1,float value2,float value3) {
        int location = getUniformLocation(name);
        GLES32.glUniform3f(location,value1,value2,value3);
        return this;
    }
    public Program setUniform(String name,Float4 value) {
        return setUniform(name,value.x,value.y,value.z,value.w);
    }
    public Program setUniform(String name,float value1,float value2,float value3,float value4) {
        int location = getUniformLocation(name);
        GLES32.glUniform4f(location,value1,value2,value3,value4);
        return this;
    }
    public Program setUniform(String name,float[] value) {
        int location = getUniformLocation(name);
        GLES32.glUniformMatrix4fv(location,1,false,value,0);
        return this;
    }
    public Program setUniform(String name,int value) {
        int location = getUniformLocation(name);
        GLES32.glUniform1i(location,value);
        return this;
    }
    public Program setUniform(String name,boolean value) {
        int location = getUniformLocation(name);
        GLES32.glUniform1i(location,value ? 1 : 0);
        return this;
    }



    public static int loadShader(int type, String resources) {
        int shaderID = GLES32.glCreateShader(type);
        if(shaderID == 0) {
            Log.e("Program","shader type:" + type);
            Log.e("Program","error:" + GLES32.glGetError());
        }
        GLES32.glShaderSource(shaderID,resources);
        GLES32.glCompileShader(shaderID);
        int[] compiled = new int[1];
        GLES32.glGetShaderiv(shaderID,GLES32.GL_COMPILE_STATUS,compiled,0);
        if(compiled[0] == 0) {
            Log.e("Program",GLES32.glGetShaderInfoLog(shaderID));
            GLES32.glDeleteShader(shaderID);
            shaderID = 0;
        }
        return shaderID;
    }
    public static int linkProgram(int vertexShader, int fragmentShader, int geometryShader) {
        int programID = GLES32.glCreateProgram();
        if(programID == 0) {
            Log.e("Program","error:" + GLES32.glGetError());
        }
        GLES32.glAttachShader(programID,vertexShader);
        GLES32.glAttachShader(programID,fragmentShader);
        if(geometryShader != 0) {
            GLES32.glAttachShader(programID,geometryShader);
        }
        GLES32.glLinkProgram(programID);
        int[] linked = new int[1];
        GLES32.glGetProgramiv(programID,GLES32.GL_LINK_STATUS,linked,0);
        if(linked[0] == 0) {
            Log.e("Program",GLES32.glGetProgramInfoLog(programID));
            GLES32.glDeleteProgram(programID);
            programID = 0;
        }
        return programID;
    }
    public static String loadFromAssetsFile(String fname, Resources resources) {
        String result = new String();
        try {
            result = loadFromInputStream(resources.getAssets().open(fname));
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }
    public static String loadFromInputStream(InputStream is) {
		String result=null;    	
		try {
			int ch=0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((ch = is.read()) != -1) {
				baos.write(ch);
			}      
			byte[] buff=baos.toByteArray();
			baos.close();
			is.close();
			result = new String(buff, "UTF-8"); 
			result = result.replaceAll("\\r\\n", "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}    	
		return result;
	}
}
