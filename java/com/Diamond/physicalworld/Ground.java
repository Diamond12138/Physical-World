package com.Diamond.physicalworld;

import com.Diamond.SGL.*;
import com.bulletphysics.dynamics.RigidBody;
import android.opengl.GLES32;
import android.content.res.Resources;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.StaticPlaneShape;

public class Ground extends Grid {
    public RigidBody rb;
    public Texture texture;
    
    public Ground(Resources r) {
        super(100,100,true);
        
        texture = new Texture(GLES32.GL_TEXTURE_2D);
        texture.bind(Texture.default_parameters, Texture.loadBitmap(R.raw.grass, r));
        
        Transform t = new Transform();
        t.setIdentity();
        DefaultMotionState dms = new DefaultMotionState(t);
        RigidBodyConstructionInfo rbci = new RigidBodyConstructionInfo(0, dms, new StaticPlaneShape(new Vector3f(0,1,0),1), new Vector3f(0, 0, 0));
        rb = new RigidBody(rbci);
    }

    @Override
    public Grid draw(Program program) {
        program.setUniform("u_enableTexture",true);
        texture.enable();
        super.draw(program);
        program.setUniform("u_enableTexture",false);
        return this;
    }
}
