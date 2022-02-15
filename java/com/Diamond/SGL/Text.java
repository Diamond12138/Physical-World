package com.Diamond.SGL;

import android.opengl.GLES32;
import android.renderscript.Float4;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Text extends Texture {
    private String mTextString;
    private Float4 mColor;
    private float mTextSize;
    
    public static final int TEXTURE_TYPE = GLES32.GL_TEXTURE_2D;
    
    public Text() {
        super(TEXTURE_TYPE);
        mTextString = new String("");
        mColor = new Float4(1,1,1,1);
        mTextSize = 10;
    }
    public Text(String str) {
        super(TEXTURE_TYPE);
        mTextString = str;
        mColor = new Float4(1,1,1,1);
        mTextSize = 25;
        bind(default_parameters,loadText(str,mColor,mTextSize,100,100));
    }
    
    
    
    public static Bitmap loadText(String string,Float4 color,float textSize,int width,int height) {
        return loadText(string,color,textSize,Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888));
    }
    public static Bitmap loadText(String string,Float4 color,float textSize,Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setARGB((int)color.w * 255,(int)color.x * 255,(int)color.y * 252,(int)color.z * 255);
        paint.setTextSize(textSize);
        paint.setTypeface(null);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        canvas.drawText(string,0,textSize,paint);
        return bitmap;
    }
    
    
    
    public Text rebind() {
        int id = genTexture();
        setTextureID(id);
        bind(default_parameters,loadText(mTextString,mColor,mTextSize,100,100));
        return this;
    }
    public Text rebind(int[] parameters,Bitmap bitmap) {
        int id = genTexture();
        setTextureID(id);
        bind(parameters,bitmap);
        return this;
    }
    
    public Text setTextSize(float textSize) {
        mTextSize = Math.abs(textSize);
        rebind();
        return this;
    }
    public float getTextSize() {
        return mTextSize;
    }
    
    public Text setText(String str) {
        mTextString = str;
        rebind();
        return this;
    }
    public String getText() {
        return mTextString;
    }
    
    public Text setTextColor(Float4 color) {
        mColor = color;
        rebind();
        return this;
    }
    public Float4 getTextColor() {
        return mColor;
    }
}
