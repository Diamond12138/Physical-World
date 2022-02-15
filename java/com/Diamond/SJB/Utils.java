package com.Diamond.SJB;

import javax.vecmath.Vector3f;
import android.renderscript.Float3;
import com.bulletphysics.collision.shapes.BoxShape;
import com.Diamond.SGL.Sprite;

public class Utils {
    public static Vector3f toVector3f(Float3 v) {
        return new Vector3f(v.x,v.y,v.z);
    }
    
    public static Float3 toFloat3(Vector3f v) {
        return new Float3(v.x,v.y,v.z);
    }
    
    public static Float3 getHalf(Sprite s) {
        Float3 scale = s.getScale();
        return com.Diamond.SGL.VectorUtil.mult(0.5f,scale);
    }
    
    public static BoxShape getBox(Sprite s) {
        return new BoxShape(toVector3f(getHalf(s)));
    }
}
