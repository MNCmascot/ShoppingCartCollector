package com.example.shoppingcartcollector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Joystick {

    private int outerCircleCenterPositionX;
    private int innerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    private int innerCircleCenterPositionY;
    private int outerCircleRadius;
    private int innerCircleRadius;
    private Paint outerCirclePaint;
    private Paint innerCirclePaint;
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius) {
        //outer and inner circle make up the joystick (inner circle shows movement)
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        //radii of circles
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        //paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.YELLOW);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);



    }


    public void draw(Canvas canvas) {
        canvas.drawCircle(outerCircleCenterPositionX, outerCircleCenterPositionY, outerCircleRadius, outerCirclePaint);
        canvas.drawCircle(innerCircleCenterPositionX, innerCircleCenterPositionY, innerCircleRadius, innerCirclePaint);
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        //draw inner circle based on percentage pulled
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);
      //  Log.d("DEBUG", "innerCircleX " + innerCircleCenterPositionX + " innerCircleY " + innerCircleCenterPositionY);

    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {

        //check if touch position is within radius of joystick
        joystickCenterToTouchDistance = Utils.getDistanceBetweenPoints(
                outerCircleCenterPositionX, outerCircleCenterPositionY,
                touchPositionX, touchPositionY);
        return joystickCenterToTouchDistance < outerCircleRadius; //true if within joystick circle
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    //set actuator x and y values, to determine how far the joystick is being pulled
    public void setActuator(double touchPositionX, double touchPositionY) {

        //distance from center of joystick to press on screen
        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;
        //absolute distance between X and Y delta values
        double deltaDistance = Utils.getDistanceBetweenPoints(0, 0, deltaX, deltaY);

        //determine % pulled
        if (deltaDistance < outerCircleRadius) {
            //Pulled within joystick radius
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        } else {
            //outside of joystick radius, divide by the distance from center
            actuatorX = deltaX / deltaDistance;
            actuatorY = deltaY / deltaDistance;
        }

      //  Log.d("DEBUG", "touchPosition X " + touchPositionX + " touchPositionY " + touchPositionY);
    //    Log.d("DEBUG", "deltaX " + deltaX + " deltaY " + deltaY);

    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }


    public int getOuterCircleCenterPositionX() { return outerCircleCenterPositionX; }

    public int getOuterCircleCenterPositionY() { return  outerCircleCenterPositionY; }

    public int getOuterCircleRadius() { return outerCircleRadius; }

    public double getActuatorX() { return actuatorX; }

    public double getActuatorY() { return actuatorY; }
}
