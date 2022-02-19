package com.Diamond.SJB;

import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.collision.dispatch.*;
import javax.vecmath.Vector3f;
import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;

public class World {
    public static int DEFAULT_MAX_PROXIES = 1024;
    public static Vector3f DEFAULT_GRAVITY = new Vector3f(0,-9.8f,0);

    public DiscreteDynamicsWorld world = null;
    public boolean isStart;

    public World(float worldSize) {
        float halfSize = worldSize / 2;
        world = createWorld(
            new Vector3f(-halfSize, -halfSize, -halfSize),
            new Vector3f(halfSize, halfSize, halfSize),
            DEFAULT_MAX_PROXIES
        );
        setGravity(DEFAULT_GRAVITY);
        stop();
    }
    
    public World setGravity(Vector3f gravity) {
        if(world != null) {
            world.setGravity(gravity);
        }
        return this;
    }
    
    public World start() {
        isStart = true;
        return this;
    }
    
    public World stop() {
        isStart = false;
        return this;
    }
    
    public World changeState() {
        isStart = !isStart;
        return this;
    }
    
    public World simulation(float fps) {
        if(isStart) {
            world.stepSimulation(1 / fps);
        }
        return this;
    }
    
    public World add(RigidSprite rs) {
        world.addRigidBody(rs.rb);
        return this;
    }

    public static DiscreteDynamicsWorld createWorld(Vector3f min, Vector3f max, int maxProxies) {
        CollisionConfiguration configuration = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(configuration);
        AxisSweep3 sweep = new AxisSweep3(min, max, maxProxies);
        SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
        return new DiscreteDynamicsWorld(dispatcher, sweep, solver, configuration);
    }
}
