package com.Diamond.physicalworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.view.WindowManager;
import android.view.Window;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.renderscript.Float3;
import com.Diamond.SGL.*;
import android.util.Log;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import java.io.IOException;
import com.Diamond.SJB.*;

public class MainActivity extends Activity {
    public MySurfaceView surfaceView;
    public RelativeLayout relativeLayout1;
    public Button goButton;
    public Button backButton;
    public Button leftButton;
    public Button rightButton;
    public Button addButton;
    public Button launchButton;
    public Button stateButton;
    public Button resetButton;
    public Button changeButton;
    public int object_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getRealMetrics(metrics);
        int SCREEN_WIDTH = metrics.widthPixels;
        int SCREEN_HEIGHT = metrics.heightPixels;
        final float min_speed = 0.1f;
        final int ready_time = 1000;
        
        object_index = 0;

        surfaceView = new MySurfaceView(this);
        surfaceView.requestFocus();
        surfaceView.setFocusableInTouchMode(true);
        surfaceView.setClickable(true);

        relativeLayout1 = (RelativeLayout)findViewById(R.id.activitymainRelativeLayout1);
        relativeLayout1.addView(surfaceView);

        goButton = new Button(this);
        goButton.setText("前进");
        goButton.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        RelativeLayout.LayoutParams gop = new RelativeLayout.LayoutParams(goButton.getLayoutParams());
        gop.setMargins(200, SCREEN_HEIGHT - 600, 400, SCREEN_HEIGHT - 400);
        goButton.setLayoutParams(gop);
        goButton.setClickable(true);
        goButton.setOnTouchListener(new View.OnTouchListener(){

                public long first;
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        first = System.currentTimeMillis();
                    }
                    float d = 1;
                    if ((System.currentTimeMillis() - first) < ready_time) {
                        d = min_speed;
                    }
                    Float3 distance = VectorUtil.mult(d, VectorUtil.normalize(VectorUtil.sub(surfaceView.renderer.camera.getCenter(), surfaceView.renderer.camera.getPosition())));
                    surfaceView.renderer.camera.move(distance);
                    //surfaceView.renderer.player.rb.setLinearVelocity(Utils.toVector3f(distance));
                    return true;
                }
            });
        relativeLayout1.addView(goButton);

        backButton = new Button(this);
        backButton.setText("后退");
        backButton.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        RelativeLayout.LayoutParams backp = new RelativeLayout.LayoutParams(backButton.getLayoutParams());
        backp.setMargins(200, SCREEN_HEIGHT - 200, 400, SCREEN_HEIGHT);
        backButton.setLayoutParams(backp);
        backButton.setClickable(true);
        backButton.setOnTouchListener(new View.OnTouchListener(){

                public long first;
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        first = System.currentTimeMillis();
                    }
                    float d = 1;
                    if ((System.currentTimeMillis() - first) < ready_time) {
                        d = min_speed;
                    }
                    Float3 distance = VectorUtil.mult(-d, VectorUtil.normalize(VectorUtil.sub(surfaceView.renderer.camera.getCenter(), surfaceView.renderer.camera.getPosition())));
                    surfaceView.renderer.camera.move(distance);
                    return true;
                }
            });
        relativeLayout1.addView(backButton);

        leftButton = new Button(this);
        leftButton.setText("左移");
        leftButton.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        RelativeLayout.LayoutParams leftp = new RelativeLayout.LayoutParams(leftButton.getLayoutParams());
        leftp.setMargins(0, SCREEN_HEIGHT - 400, 200, SCREEN_HEIGHT - 200);
        leftButton.setLayoutParams(leftp);
        leftButton.setClickable(true);
        leftButton.setOnTouchListener(new View.OnTouchListener(){

                public long first;
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        first = System.currentTimeMillis();
                    }
                    float d = 1;
                    if ((System.currentTimeMillis() - first) < ready_time) {
                        d = min_speed;
                    }
                    Float3 center = surfaceView.renderer.camera.getCenter();
                    Float3 position = surfaceView.renderer.camera.getPosition();
                    Float3 up = VectorUtil.add(position, surfaceView.renderer.camera.getUp());
                    Float3 A = VectorUtil.sub(up, position);
                    Float3 B = VectorUtil.sub(center, position);
                    Float3 distance = VectorUtil.mult(d, VectorUtil.normalize((VectorUtil.cross(A, B))));
                    surfaceView.renderer.camera.move(distance);
                    return true;
                }
            });
        relativeLayout1.addView(leftButton);

        rightButton = new Button(this);
        rightButton.setText("右移");
        rightButton.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        RelativeLayout.LayoutParams rightp = new RelativeLayout.LayoutParams(rightButton.getLayoutParams());
        rightp.setMargins(400, SCREEN_HEIGHT - 400, 600, SCREEN_HEIGHT - 200);
        rightButton.setLayoutParams(rightp);
        rightButton.setClickable(true);
        rightButton.setOnTouchListener(new View.OnTouchListener(){

                public long first;
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        first = System.currentTimeMillis();
                    }
                    float d = 1;
                    if ((System.currentTimeMillis() - first) < ready_time) {
                        d = min_speed;
                    }
                    Float3 center = surfaceView.renderer.camera.getCenter();
                    Float3 position = surfaceView.renderer.camera.getPosition();
                    Float3 up = VectorUtil.add(position, surfaceView.renderer.camera.getUp());
                    Float3 A = VectorUtil.sub(center, position);
                    Float3 B = VectorUtil.sub(up, position);
                    Float3 distance = VectorUtil.mult(d, VectorUtil.normalize(VectorUtil.cross(A, B)));
                    surfaceView.renderer.camera.move(distance);
                    return true;
                }
            });
        relativeLayout1.addView(rightButton);
        
        stateButton = new Button(this);
        stateButton.setText("开始\n模拟");
        stateButton.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        RelativeLayout.LayoutParams statep = new RelativeLayout.LayoutParams(stateButton.getLayoutParams());
        statep.setMargins(SCREEN_WIDTH - 200, 0, SCREEN_WIDTH, 200);
        stateButton.setLayoutParams(statep);
        stateButton.setClickable(true);
        stateButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    surfaceView.renderer.world.changeState();
                    if(surfaceView.renderer.world.isStart) {
                        stateButton.setText("停止\n模拟");
                    } else {
                        stateButton.setText("开始\n模拟");
                    }
                }
            });
        relativeLayout1.addView(stateButton);
        
        addButton = new Button(this);
        addButton.setText("添加");
        addButton.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        RelativeLayout.LayoutParams addp = new RelativeLayout.LayoutParams(addButton.getLayoutParams());
        addp.setMargins(SCREEN_WIDTH - 200, SCREEN_HEIGHT - 400, SCREEN_WIDTH, SCREEN_HEIGHT - 200);
        addButton.setLayoutParams(addp);
        addButton.setClickable(true);
        addButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(object_index == 0) {
                        AddUtil.addCube(surfaceView,1);
                    } else {
                        AddUtil.addSphere(surfaceView,1);
                    }
                }
            });
        relativeLayout1.addView(addButton);
        
        launchButton = new Button(this);
        launchButton.setText("发射");
        launchButton.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        RelativeLayout.LayoutParams launchp = new RelativeLayout.LayoutParams(launchButton.getLayoutParams());
        launchp.setMargins(SCREEN_WIDTH - 200, SCREEN_HEIGHT - 200, SCREEN_WIDTH, SCREEN_HEIGHT);
        launchButton.setLayoutParams(launchp);
        launchButton.setClickable(true);
        launchButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Float3 v = VectorUtil.normalize(VectorUtil.sub(surfaceView.renderer.camera.getCenter(),surfaceView.renderer.camera.getPosition()));
                    v = VectorUtil.mult(5,v);
                    if(object_index == 0) {
                        AddUtil.launchCube(surfaceView,1,v);
                    } else {
                        AddUtil.launchSphere(surfaceView,1,v);
                    }
                }
            });
        launchButton.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    Float3 v = VectorUtil.normalize(VectorUtil.sub(surfaceView.renderer.camera.getCenter(),surfaceView.renderer.camera.getPosition()));
                    v = VectorUtil.mult(10,v);
                    if(object_index == 0) {
                        AddUtil.launchCube(surfaceView,5,v);
                    } else {
                        AddUtil.launchSphere(surfaceView,5,v);
                    }
                    return false;
                }
            });
        relativeLayout1.addView(launchButton);
        
        resetButton = new Button(this);
        resetButton.setText("重置");
        resetButton.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        RelativeLayout.LayoutParams resetp = new RelativeLayout.LayoutParams(resetButton.getLayoutParams());
        resetp.setMargins(0, 0, 200, 200);
        resetButton.setLayoutParams(resetp);
        resetButton.setClickable(true);
        resetButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    surfaceView.renderer.rigidSprites.clear();
                    surfaceView.renderer.world = new World(10000);
                    surfaceView.renderer.world.world.addRigidBody(surfaceView.renderer.ground.rb);
                    stateButton.setText("开始\n模拟");
                }
            });
        relativeLayout1.addView(resetButton);
        
        changeButton = new Button(this);
        changeButton.setText("改变\n物体");
        changeButton.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        RelativeLayout.LayoutParams changep = new RelativeLayout.LayoutParams(changeButton.getLayoutParams());
        changep.setMargins(SCREEN_WIDTH - 200, SCREEN_HEIGHT - 600, SCREEN_WIDTH, SCREEN_HEIGHT - 400);
        changeButton.setLayoutParams(changep);
        changeButton.setClickable(true);
        changeButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    object_index += 1;
                    if(object_index > 1) {
                        object_index = 0;
                    }
                }
            });
        relativeLayout1.addView(changeButton);
        
    }

    @Override
    protected void onResume() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
} 
