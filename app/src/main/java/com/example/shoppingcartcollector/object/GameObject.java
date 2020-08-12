package com.example.shoppingcartcollector.object;

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
    protected double getPositionX() { return positionX; }
    //Get Y position of GameObject
    protected double getPositionY() { return positionY; }

    //Set position of the GameObject
    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    //Get the distance between to objects
    protected static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }

}
