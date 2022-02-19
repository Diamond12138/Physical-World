package com.Diamond.physicalworld;

import com.Diamond.SJB.*;
import com.Diamond.SGL.*;
import java.util.Random;
import android.renderscript.*;
import com.bulletphysics.collision.shapes.*;
import javax.vecmath.Vector3f;

public class AddUtil {
    public static RigidSprite createRigidSprite(MySurfaceView msv, int index) {
        ObjSprite os = msv.renderer.objSprites[index];
        RigidSprite rs = new RigidSprite(os.VAO, os.vCount);
        return rs;
    }

    public static void addRigidSprite(MySurfaceView msv, RigidSprite rs) {
        msv.renderer.rigidSprites.add(rs);
        msv.renderer.world.add(rs);
    }

    public static Float4 randomColor() {
    	Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Float4(r, g, b, 1);
    }
    
    
    
    public static void addCube(MySurfaceView msv, float mass) {
    	RigidSprite rs = AddUtil.createRigidSprite(msv, 0);
        rs.setPosition(msv.renderer.camera.getCenter());
        rs.bind(mass, Utils.getBox(rs));
        rs.color = AddUtil.randomColor();
        AddUtil.addRigidSprite(msv, rs);
    }
    public static void launchCube(MySurfaceView msv, float mass, Float3 v) {
    	RigidSprite rs = AddUtil.createRigidSprite(msv, 0);
        rs.setPosition(msv.renderer.camera.getCenter());
        rs.bind(mass, Utils.getBox(rs));
        rs.rb.setLinearVelocity(Utils.toVector3f(v));
        rs.color = AddUtil.randomColor();
        AddUtil.addRigidSprite(msv, rs);
    }
    
    
    
    public static void addSphere(MySurfaceView msv, float mass) {
    	RigidSprite rs = AddUtil.createRigidSprite(msv, 1);
        rs.setPosition(msv.renderer.camera.getCenter());
        rs.bind(mass, new SphereShape(1f));
        rs.color = AddUtil.randomColor();
        AddUtil.addRigidSprite(msv, rs);
    }
    public static void launchSphere(MySurfaceView msv, float mass, Float3 v) {
    	RigidSprite rs = AddUtil.createRigidSprite(msv, 1);
        rs.setPosition(msv.renderer.camera.getCenter());
        rs.bind(mass, new SphereShape(1f));
        rs.rb.setLinearVelocity(Utils.toVector3f(v));
        rs.color = AddUtil.randomColor();
        AddUtil.addRigidSprite(msv, rs);
    }
    
    
    
    public static void addCylinder(MySurfaceView msv, float mass) {
    	RigidSprite rs = AddUtil.createRigidSprite(msv, 2);
        rs.setPosition(msv.renderer.camera.getCenter());
        rs.bind(mass, new CylinderShape(new Vector3f(0.5f,1.0f,0.5f)));
        rs.color = AddUtil.randomColor();
        AddUtil.addRigidSprite(msv, rs);
    }
    public static void launchCylinder(MySurfaceView msv, float mass, Float3 v) {
    	RigidSprite rs = AddUtil.createRigidSprite(msv, 2);
        rs.setPosition(msv.renderer.camera.getCenter());
        rs.bind(mass, new CylinderShape(new Vector3f(0.5f,1.0f,0.5f)));
        rs.rb.setLinearVelocity(Utils.toVector3f(v));
        rs.color = AddUtil.randomColor();
        AddUtil.addRigidSprite(msv, rs);
    }
}
