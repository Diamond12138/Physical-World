package com.Diamond.SGL;

import android.renderscript.Float3;
import android.renderscript.Matrix4f;
import android.opengl.Matrix;

public class Camera {
    private Float3 mPosition;
    private Float3 mCenter;
    private Float3 mUp;

    private Matrix4f mProjection;
    private Matrix4f mView;

    private float mAngleX;
    private float mAngleY;
    private float mDistance;
    private int mOrbitMode;

    public static final int ORBIT_CENTER = 0;
    public static final int ORBIT_POSITION = 1;

    public Camera() {
        mPosition = new Float3(0, 0, 0);
        mCenter = new Float3(0, 0, -1);
        mUp = new Float3(0, 1, 0);

        mProjection = new Matrix4f();
        mProjection.loadIdentity();

        mView = new Matrix4f();
        mView.loadIdentity();

        update();

        mAngleX = 0;
        mAngleY = 0;
        mDistance = 0;
        mOrbitMode = ORBIT_POSITION;
    }



    public Camera update() {
        float[] view = new float[16];
        Matrix.setLookAtM(view, 0,
                          mPosition.x, mPosition.y, mPosition.z,
                          mCenter.x, mCenter.y, mCenter.z,
                          mUp.x, mUp.y, mUp.z);
        mView = new Matrix4f(view);
        return this;
    }



    public Float3 getPosition() {
        return mPosition;
    }
    public Camera setPosition(Float3 value) {
        mPosition = value;
        updateOrbit();
        return this;
    }
    public Camera setPositionX(float value) {
        mPosition.x = value;
        updateOrbit();
        return this;
    }
    public Camera setPositionY(float value) {
        mPosition.y = value;
        updateOrbit();
        return this;
    }
    public Camera setPositionZ(float value) {
        mPosition.z = value;
        updateOrbit();
        return this;
    }
    public Camera move(Float3 value) {
        mPosition.x += value.x;
        mPosition.y += value.y;
        mPosition.z += value.z;
        updateOrbit();
        return this;
    }
    public Camera moveX(float value) {
        mPosition.x += value;
        updateOrbit();
        return this;
    }
    public Camera moveY(float value) {
        mPosition.y += value;
        updateOrbit();
        return this;
    }
    public Camera moveZ(float value) {
        mPosition.z += value;
        updateOrbit();
        return this;
    }



    public Float3 getCenter() {
        return mCenter;
    }
    public Camera setCenter(Float3 value) {
        mCenter = value;
        updateOrbit();
        return this;
    }
    public Camera setCenterX(float value) {
        mCenter.x = value;
        updateOrbit();
        return this;
    }
    public Camera setCenterY(float value) {
        mCenter.y = value;
        updateOrbit();
        return this;
    }
    public Camera setCenterZ(float value) {
        mCenter.z = value;
        updateOrbit();
        return this;
    }
    public Camera moveCenter(Float3 value) {
        mCenter.x += value.x;
        mCenter.y += value.y;
        mCenter.z += value.z;
        updateOrbit();
        return this;
    }
    public Camera moveCenterX(float value) {
        mCenter.x += value;
        updateOrbit();
        return this;
    }
    public Camera moveCenterY(float value) {
        mCenter.y += value;
        updateOrbit();
        return this;
    }
    public Camera moveCenterZ(float value) {
        mCenter.z += value;
        updateOrbit();
        return this;
    }



    public Float3 getUp() {
        return mUp;
    }
    public Camera setUp(Float3 value) {
        mUp = value;
        update();
        return this;
    }
    public Camera setUpX(float value) {
        mUp.x = value;
        update();
        return this;
    }
    public Camera setUpY(float value) {
        mUp.y = value;
        update();
        return this;
    }
    public Camera setUpZ(float value) {
        mUp.z = value;
        update();
        return this;
    }
    public Camera moveUp(Float3 value) {
        mUp.x += value.x;
        mUp.y += value.y;
        mUp.z += value.z;
        update();
        return this;
    }
    public Camera moveUpX(float value) {
        mUp.x += value;
        update();
        return this;
    }
    public Camera moveUpY(float value) {
        mUp.y += value;
        update();
        return this;
    }
    public Camera moveUpZ(float value) {
        mUp.z += value;
        update();
        return this;
    }



    public Camera updateOrbit() {
        if (mAngleY >= 90) {
            mAngleY = 89.9f;
        } else if (mAngleY <= -90) {
            mAngleY = -89.9f;
        }

        float x = 0;
        float y = 0;
        float z = 0;
        if (mOrbitMode == ORBIT_CENTER) {
            y = (float)Math.sin(Math.toRadians(mAngleY)) * mDistance;
            float h = (float)Math.cos(Math.toRadians(mAngleY)) * mDistance;

            x = (float)Math.sin(Math.toRadians(mAngleX)) * h;
            z = (float)Math.cos(Math.toRadians(mAngleX)) * h;

            Float3 position = new Float3(x, y, z);
            mPosition = VectorUtil.add(mCenter, position);
        } else {
            y = (float)Math.sin(Math.toRadians(mAngleY));
            float h = (float)Math.cos(Math.toRadians(mAngleY));

            x = (float)Math.sin(Math.toRadians(mAngleX)) * h;
            z = (float)Math.cos(Math.toRadians(mAngleX)) * h;

            Float3 center = VectorUtil.normalize(new Float3(x, y, z));
            mCenter = VectorUtil.add(mPosition, center);
        }
        update();
        return this;
    }
    public Camera setOrbit(float angleX, float angleY, float distance) {
        mAngleX = angleX;
        mAngleY = angleY;
        mDistance = distance;
        updateOrbit();
        return this;
    }
    public Camera setOrbit(float angleX, float angleY) {
        mAngleX = angleX;
        mAngleY = angleY;
        updateOrbit();
        return this;
    }
    public float getAngleX() {
        return mAngleX;
    }
    public float getAngleY() {
        return mAngleY;
    }
    public float getDistance() {
        return mDistance;
    }
    public Camera setAngleX(float value) {
    	mAngleX = value;
    	updateOrbit();
    	return this;
    }
    public Camera setAngleY(float value) {
    	mAngleY = value;
    	updateOrbit();
    	return this;
    }
    public Camera setDistance(float value) {
    	mDistance = value;
    	updateOrbit();
    	return this;
    }
    public Camera rotate(float x, float y) {
    	mAngleX += x;
    	mAngleY += y;
    	updateOrbit();
    	return this;
    }
    public Camera rotateX(float value) {
    	mAngleX += value;
    	updateOrbit();
    	return this;
    }
    public Camera rotateY(float value) {
    	mAngleY += value;
    	updateOrbit();
    	return this;
    }
    public int getOrbitMode() {
        return mOrbitMode;
    }
    public Camera setOrbitMode(int mode) {
        mOrbitMode = mode;
        return this;
    }



    public Matrix4f getViewMatrix() {
        return mView;
    }
    public float[] getViewMatrixArray() {
        return getViewMatrix().getArray();
    }
    public Camera setViewMatrix(Matrix4f matrix) {
        mView = matrix;
        return this;
    }
    public Camera setView(Float3 position, Float3 center, Float3 up) {
        mPosition = position;
        mCenter = center;
        mUp = up;
        update();
        return this;
    }



    public Matrix4f getProjectionMatrix() {
        return mProjection;
    }
    public float[] getProjectionMatrixArray() {
        return getProjectionMatrix().getArray();
    }
    public Camera setProjectionMatrix(Matrix4f matrix) {
        mProjection = matrix;
        return this;
    }
    public Camera setPerspective(float fovy, float aspect, float near, float far) {
        mProjection.loadIdentity();
        mProjection.loadPerspective(fovy, aspect, near, far);
        return this;
    }
    public Camera setOrtho(float left, float right, float bottom, float top, float near, float far) {
        mProjection.loadIdentity();
        mProjection.loadOrtho(left, right, bottom, top, near, far);
        return this;
    }



    public Camera draw(Program program) {
        program.setUniform("u_projection", getProjectionMatrixArray());
        program.setUniform("u_view", getViewMatrixArray());
        return this;
    }
}
