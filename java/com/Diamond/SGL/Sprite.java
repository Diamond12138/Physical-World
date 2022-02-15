package com.Diamond.SGL;

import android.renderscript.Float3;
import android.renderscript.Matrix4f;

public class Sprite {
    private Float3 mPosition;
    private Float3 mRotate;
    private Float3 mScale;
    private Matrix4f mMatrix;
    
    public Sprite() {
        mPosition = new Float3(0,0,0);
        mRotate = new Float3(0,0,0);
        mScale = new Float3(1,1,1);
        mMatrix = new Matrix4f();
    }
    
    
    
    public Sprite update() {
        mMatrix.loadIdentity();
        mMatrix.translate(mPosition.x,mPosition.y,mPosition.z);
        mMatrix.rotate(mRotate.x,1,0,0);
        mMatrix.rotate(mRotate.y,0,1,0);
        mMatrix.rotate(mRotate.z,0,0,1);
        mMatrix.scale(mScale.x,mScale.y,mScale.z);
        return this;
    }
    
    
    
    public Matrix4f getMatrix() {
        return mMatrix;
    }
    public Sprite setMatrix(Matrix4f matrix) {
        mMatrix = matrix;
        return this;
    }
    public float[] getMatrixArray() {
        return getMatrix().getArray();
    }
    public Sprite setMatrix(float[] matrix) {
        mMatrix.loadIdentity();
        mMatrix.load(new Matrix4f(matrix));
        return this;
    }
    
    
    
    public Float3 getPosition() {
        return mPosition;
    }
    public Sprite setPosition(Float3 value) {
        mPosition = value;
        update();
        return this;
    }
    public Sprite setPositionX(float value) {
        mPosition.x = value;
        update();
        return this;
    }
    public Sprite setPositionY(float value) {
        mPosition.y = value;
        update();
        return this;
    }
    public Sprite setPositionZ(float value) {
        mPosition.z = value;
        update();
        return this;
    }
    public Sprite translate(Float3 value) {
        mPosition.x += value.x;
        mPosition.y += value.y;
        mPosition.z += value.z;
        update();
        return this;
    }
    public Sprite translateX(float value) {
        mPosition.x += value;
        update();
        return this;
    }
    public Sprite translateY(float value) {
        mPosition.y += value;
        update();
        return this;
    }
    public Sprite translateZ(float value) {
        mPosition.z += value;
        update();
        return this;
    }
    
    
    
    public Float3 getRotate() {
        return mRotate;
    }
    public Sprite setRotate(Float3 value) {
        mRotate = value;
        update();
        return this;
    }
    public Sprite setRotateX(float value) {
        mRotate.x = value;
        update();
        return this;
    }
    public Sprite setRotateY(float value) {
        mRotate.y = value;
        update();
        return this;
    }
    public Sprite setRotateZ(float value) {
        mRotate.z = value;
        update();
        return this;
    }
    public Sprite rotate(Float3 value) {
        mRotate.x += value.x;
        mRotate.y += value.y;
        mRotate.z += value.z;
        update();
        return this;
    }
    public Sprite rotateX(float value) {
        mRotate.x += value;
        update();
        return this;
    }
    public Sprite rotateY(float value) {
        mRotate.y += value;
        update();
        return this;
    }
    public Sprite rotateZ(float value) {
        mRotate.z += value;
        update();
        return this;
    }
    
    
    
    public Float3 getScale() {
        return mScale;
    }
    public Sprite setScale(float value) {
        setScale(new Float3(value,value,value));
        return this;
    }
    public Sprite setScale(Float3 value) {
        mScale = value;
        update();
        return this;
    }
    public Sprite setScaleX(float value) {
        mScale.x = value;
        update();
        return this;
    }
    public Sprite setScaleY(float value) {
        mScale.y = value;
        update();
        return this;
    }
    public Sprite setScaleZ(float value) {
        mScale.z = value;
        update();
        return this;
    }
    public Sprite scale(Float3 value) {
        mScale.x += value.x;
        mScale.y += value.y;
        mScale.z += value.z;
        update();
        return this;
    }
    public Sprite scaleX(float value) {
        mScale.x += value;
        update();
        return this;
    }
    public Sprite scaleY(float value) {
        mScale.y += value;
        update();
        return this;
    }
    public Sprite scaleZ(float value) {
        mScale.z += value;
        update();
        return this;
    }
}
