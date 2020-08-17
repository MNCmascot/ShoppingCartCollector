package com.example.shoppingcartcollector.gameobject;

/*
GameObject abstract class for other basic objects to inherit from:
carts / player
 */

import android.graphics.Canvas;

import com.example.shoppingcartcollector.Utils;

public abstract class GameObject {
    protected double positionX;
    protected double positionY;
    protected double velocityX = 0;
    protected double velocityY = 0;
    private double directionX = 1;
    private double directionY = 1;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }


    //Method checks if 2 circular GameObjects are colliding based on positions and radii
    public static boolean areCirclesColliding(GameObject obj1, double rad1, GameObject obj2, double rad2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = rad1 + rad2;

        //circles inside each other?
        if (distance < distanceToCollision) return true;
        else return false;
    }


    public abstract void draw(Canvas canvas);
    public abstract void update();

    //Get X position of GameObject
    public double getPositionX() { return positionX; }
    //Get Y position of GameObject
    public double getPositionY() { return positionY; }

    //Set position of the GameObject
    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    //Get the distance between two objects
    public static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Utils.getDistanceBetweenPoints(obj1.getPositionX(), obj1.getPositionY(),
                obj2.getPositionX(), obj2.getPositionY());
    }

    protected double getDirectionX() {
        return directionX;
    }

    protected double getDirectionY() {
        return directionY;
    }


}
