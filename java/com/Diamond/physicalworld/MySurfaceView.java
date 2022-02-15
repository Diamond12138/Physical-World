package com.Diamond.physicalworld;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import android.content.Context;
import android.renderscript.*;
import android.opengl.GLES32;
import android.view.MotionEvent;
import java.io.IOException;
import android.content.res.AssetManager;
import android.graphics.RectF;
import com.Diamond.SGL.*;
import android.util.Log;
import com.Diamond.SJB.*;
import android.content.res.Resources;
import com.bulletphysics.collision.shapes.BoxShape;
import java.util.Random;
import java.util.ArrayList;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.linearmath.Transform;

public class MySurfaceView extends GLSurfaceView {
    public class MyRenderer implements GLSurfaceView.Renderer {
        public Program usualProgram;
        public Program lightingProgram;

        public Axis axis;
        public Camera camera;
        public GLES32 gl;

        public SkyBox skybox;

        public ObjSprite[] objSprites;

        public Ground ground;
        
        public RigidSprite player;

        public ArrayList<RigidSprite> rigidSprites;

        public World world;
        
        public static final float WORLD_SIZE = 1000;

        @Override
        public void onSurfaceCreated(GL10 p1, EGLConfig p2) {
            usualProgram = new Program("shader/v.vert", "shader/usual.frag", null, getResources());
            lightingProgram = new Program("shader/v.vert", "shader/lighting.frag", null, getResources());

            axis = new Axis();
            axis.setScale(0.001f);

            camera = new Camera();
            camera.setView(new Float3(0, 5, -10), new Float3(0, 0, 0), new Float3(0, 1, 0));
            camera.setPerspective(120.0f, 0.5f, 0.1f, WORLD_SIZE);
            camera.setOrbit(0, 0, 2);

            try {
                AssetManager am = getResources().getAssets();
                Resources r = getResources();

                skybox = new SkyBox(am.open("model/cube.obj"), r);
                skybox.setScale(WORLD_SIZE / 2);

                objSprites = new ObjSprite[3];
                objSprites[0] = new ObjSprite(am.open("model/cube.obj"));
                objSprites[1] = new ObjSprite(am.open("model/sphere.obj"));
                objSprites[2] = new ObjSprite(am.open("model/cylinder.obj"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            world = new World(WORLD_SIZE);
            world.stop();

            ground = new Ground(getResources());
            ground.setPositionY(1).setScale(new Float3(WORLD_SIZE,0,WORLD_SIZE));
            world.world.addRigidBody(ground.rb);
            
            /*player = new RigidSprite(objSprites[1].VAO,objSprites[1].vCount);
            player.setPosition(camera.getPosition());
            player.bind(1,new SphereShape(0.5f));
            world.add(player);*/

            rigidSprites = new ArrayList<RigidSprite>();

            for (int i = 0; i < 1000; i++) {
                RigidSprite rs = new RigidSprite(objSprites[0].VAO, objSprites[0].vCount);

                rs.color = AddUtil.randomColor();

                rs.setPosition(new Float3(i / 100 - 5,i / 10 % 10 + 2,i % 10 - 5));
                rs.bind(1, Utils.getBox(rs));

                rigidSprites.add(rs);
                world.add(rs);
            }

            gl.glEnable(gl.GL_DEPTH_TEST);
            gl.glEnable(gl.GL_CULL_FACE);
        }

        @Override
        public void onSurfaceChanged(GL10 p1, int p2, int p3) {
            gl.glViewport(0, 0, p2, p3);
            camera.setPerspective(120.0f, (float)p2 / (float)p3, 0.1f, WORLD_SIZE);
        }

        @Override
        public void onDrawFrame(GL10 p1) {
            try {

                world.simulation(60);
                /*player.updateState();
                camera.setPosition(player.getPosition());*/

                if (camera.getPosition().y < 1) {
                    camera.setPositionY(1);
                }

                gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
                gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);



                usualProgram.useProgram();
                camera.draw(usualProgram);

                usualProgram.setUniform("u_enableTexture", false);
                gl.glLineWidth(10);
                axis.setPosition(camera.getCenter());
                axis.draw(usualProgram);
                gl.glLineWidth(1);

                usualProgram.setUniform("u_enableTexture", true);
                skybox.setPosition(camera.getPosition());
                gl.glFrontFace(gl.GL_CW);
                skybox.draw(usualProgram);



                lightingProgram.useProgram();
                lightingProgram.setUniform("u_lightposition", new Float3(-100, 25, -50));
                lightingProgram.setUniform("u_cameraposition", camera.getPosition());
                lightingProgram.setUniform("u_constant", 1.0f);
                lightingProgram.setUniform("u_linear", 0.01f);
                lightingProgram.setUniform("u_constant", 0.0001f);
                camera.draw(lightingProgram);
                gl.glFrontFace(gl.GL_CCW);
                ground.draw(lightingProgram);
                for (int i = 0; i < rigidSprites.size(); i++) {
                    RigidSprite rs = rigidSprites.get(i);
                    lightingProgram.setUniform("u_color", rs.color);
                    rs.updateState().draw(lightingProgram);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public MyRenderer renderer;
    public float lastX,lastY;
    public MySurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(3);
        renderer = new MyRenderer();
        setRenderer(renderer);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = event.getX();
            lastY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float nowX = event.getX();
            float nowY = event.getY();

            float dx = nowX - lastX;
            float dy = nowY - lastY;

            dx *= 0.1f;
            dy *= 0.1f;

            renderer.camera.rotate(-dx, -dy);

            lastX = nowX;
            lastY = nowY;
        }
        return super.onTouchEvent(event);
    }
}
