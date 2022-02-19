package com.Diamond.SGL;

import android.opengl.GLES32;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import java.io.InputStream;
import java.io.IOException;
import android.content.res.Resources;
import android.content.res.AssetManager;

public class Texture {
    private int mTextureID;
	private int mType;

	public Texture(int type) {
        mTextureID = genTexture();
        mType = type;
    }

    public static int[] default_parameters = {GLES32.GL_NEAREST,GLES32.GL_NEAREST,GLES32.GL_REPEAT,GLES32.GL_REPEAT,GLES32.GL_REPEAT,GLES32.GL_REPEAT};
	public static void texParameter(int type, int[] parameters) {
		GLES32.glTexParameterf(type, GLES32.GL_TEXTURE_MIN_FILTER, parameters[0]);
		GLES32.glTexParameterf(type, GLES32.GL_TEXTURE_MAG_FILTER, parameters[1]);
		GLES32.glTexParameterf(type, GLES32.GL_TEXTURE_WRAP_S, parameters[2]);
		GLES32.glTexParameterf(type, GLES32.GL_TEXTURE_WRAP_T, parameters[3]);
        if (type == GLES32.GL_TEXTURE_CUBE_MAP) {
            GLES32.glTexParameterf(type, GLES32.GL_TEXTURE_WRAP_R, parameters[4]);
        }
        GLES32.glGenerateMipmap(type);
	}
	public static int genTexture() {
		int[] texture = new int[1];
		GLES32.glGenTextures(1, texture, 0);
		return texture[0];
	}
    public static Bitmap loadBitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }
    public static Bitmap loadBitmap(int raw, Resources resources) {
        return loadBitmap(resources.openRawResource(raw));
    }
    public static Bitmap loadBitmap(String fname, Resources resources) {
        return loadBitmap(fname, resources.getAssets());
    }
    public static Bitmap loadBitmap(String fname, AssetManager assetManager) {
        Bitmap result = null;
        try {
            result = loadBitmap(assetManager.open(fname));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Bitmap loadBitmap(String path) {
        return BitmapFactory.decodeFile(path);
    }
    public static void texImage(int type, Bitmap bitmap) {
        GLUtils.texImage2D(type, 0, bitmap, 0);
    }
    public static void texImage(int type, Bitmap[] bitmaps) {
        if (type == GLES32.GL_TEXTURE_CUBE_MAP) {
            for (int i = 0;i < bitmaps.length;i++) {
                GLUtils.texImage2D(GLES32.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, bitmaps[i], 0);
            }
        }
    }

    public Texture bind(int[] parameters, Bitmap bitmap) {
        GLES32.glBindTexture(mType, mTextureID);
        texParameter(mType, parameters);
        texImage(mType, bitmap);
        bitmap.recycle();
        return this;
    }
    public Texture bind(int[] parameters, Bitmap[] bitmaps) {
        GLES32.glBindTexture(mType, mTextureID);
        texParameter(mType, parameters);
        texImage(mType, bitmaps);
        for (int i = 0;i < bitmaps.length;i++) {
            bitmaps[i].recycle();
        }
        return this;
    }

	public Texture enable() {
		GLES32.glBindTexture(mType, mTextureID);
		return this;
	}

    public Texture delete() {
        GLES32.glDeleteTextures(1, new int[]{ mTextureID }, 0);
        return this;
    }

    public Texture setType(int type) {
        mType = type;
        return this;
    }
    public int getType() {
        return mType;
    }
    public Texture setTextureID(int id) {
        delete();
        mTextureID = id;
        return this;
    }
    public int getTextureID() {
        return mTextureID;
    }
}
