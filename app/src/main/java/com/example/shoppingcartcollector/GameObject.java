package com.example.shoppingcartcollector;

/*
GameObject abstract class for other basic objects to inherit from:
carts / player
 */

import android.graphics.Canvas;

public abstract class GameObject {
    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void draw(Canvas canvas);
    public abstract void update();
}
