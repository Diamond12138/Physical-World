package com.Diamond.SGL;

import java.util.ArrayList;
import android.opengl.GLES32;
import android.util.Log;
import android.renderscript.Float3;

public class Instance {
    public ArrayList<Sprite> sprites;
    public int vCount;
    
    public Instance(int vcount) {
        vCount = vcount;
        sprites = new ArrayList<Sprite>();
    }
    
    public Instance add(Sprite sprite) {
        sprites.add(sprite);
        return this;
    }
    
    public Sprite get(int index) {
        return sprites.get(index);
    }
    
    public Instance remove(int index) {
        sprites.remove(index);
        return this;
    }
    
    public Instance remove(Sprite sprite) {
        for (int i = 0; i < sprites.size(); i++) {
            if(VectorUtil.equal(sprite.getPosition(),sprites.get(i).getPosition())) {
                remove(i);
                break;
            }
        }
        return this;
    }
    
    public Instance draw(Program program) {
        GLES32.glDrawArraysInstanced(GLES32.GL_TRIANGLES,0,vCount,sprites.size());
        return this;
    }
}
