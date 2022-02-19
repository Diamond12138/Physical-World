package com.Diamond.SJB;

import com.Diamond.SGL.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.linearmath.*;
import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.CollisionShape;
import android.renderscript.*;
import javax.vecmath.Quat4f;

public class RigidSprite extends ObjSprite {
    public RigidBody rb = null;
    public Float4 color = null;

    public RigidSprite(int vao, int vcount) {
        super(vao, vcount);
    }

    public RigidSprite bind(float mass, CollisionShape shape) {
        Transform t = new Transform();
        t.setIdentity();
        t.setFromOpenGLMatrix(getMatrixArray());
        DefaultMotionState dms = new DefaultMotionState(t);
        Vector3f inertia = new Vector3f();
        shape.calculateLocalInertia(mass, inertia);
        RigidBodyConstructionInfo rbci = new RigidBodyConstructionInfo(mass, dms, shape, inertia);
        rb = new RigidBody(rbci);
        return this;
    }

    public RigidSprite bindStatic(CollisionShape shape) {
        Transform t = new Transform();
        t.setIdentity();
        DefaultMotionState dms = new DefaultMotionState(t);
        RigidBodyConstructionInfo rbci = new RigidBodyConstructionInfo(0, dms, shape, new Vector3f(0, 0, 0));
        rb = new RigidBody(rbci);
        return this;
    }

    public RigidSprite updateState() {
        Transform t = rb.getMotionState().getWorldTransform(new Transform());

        setPosition(Utils.toFloat3(t.origin));

        Quat4f quat = new Quat4f();
        t.getRotation(quat);
        setRotate(new Float3(quat.x * quat.w, quat.y * quat.w, quat.z * quat.w));
        
        float[] matrix = new float[16];
        t.getOpenGLMatrix(matrix);
        setMatrix(matrix);
        return this;
    }
}
