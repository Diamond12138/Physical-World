package com.Diamond.SGL;

import android.renderscript.Float3;

public class VectorUtil {
    public static Float3 add(Float3 a, Float3 b) {
        float x = a.x + b.x;
        float y = a.y + b.y;
        float z = a.z + b.z;
        return new Float3(x, y, z);
    }

    public static Float3 sub(Float3 a, Float3 b) {
        return add(a, mult(-1, b));
    }

    public static Float3 mult(float k, Float3 v) {
        float x = k * v.x;
        float y = k * v.y;
        float z = k * v.z;
        return new Float3(x, y, z);
    }

    public static float dot(Float3 a, Float3 b) {
        float x = a.x * b.x;
        float y = a.y * b.y;
        float z = a.z * b.z;
        return x + y + z;
    }

    public static Float3 cross(Float3 a, Float3 b) {
        float x = a.y * b.z - a.z * b.y;
        float y = a.z * b.x - a.x * b.z;
        float z = a.x * b.y - a.y * b.x;
        return new Float3(x, y, z);
    }

    public static float mod(Float3 v) {
        float x = v.x * v.x;
        float y = v.y * v.y;
        float z = v.z * v.z;
        return (float)Math.sqrt(x + y + z);
    }

    public static Float3 normalize(Float3 v) {
        float mod = mod(v);
        float x = v.x / mod;
        float y = v.y / mod;
        float z = v.z / mod;
        return new Float3(x, y, z);
    }

    public static Float3 calculateNormal(Float3 a, Float3 b, Float3 c) {
        Float3 A = sub(b, a);
        Float3 B = sub(c, a);
        return normalize(cross(A, B));
    }

    public static Float3 toFloat3(float[] array) {
        if (array.length >= 3) {
            return new Float3(array[0], array[1], array[2]);
        } else {
            return null;
        }
    }

    public static float[] toArray(Float3 v) {
        return new float[] { v.x,v.y,v.z };
    }

    public static boolean equal(Float3 a, Float3 b) {
        return (
                (Math.abs(a.x - b.x) < 0.0001f) &&
                (Math.abs(a.y - b.y) < 0.0001f) &&
                (Math.abs(a.z - b.z) < 0.0001f)
            );
    }
}
