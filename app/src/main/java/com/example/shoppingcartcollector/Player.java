package com.example.shoppingcartcollector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

/*
   Main character of the game, controlled by the joystick.
   extension of GameObject


 */

public class Player extends GameObject{
    private static final double SPEED_PIXELS_PER_SECOND = 400.0; //actual speed value
    //determine max speed based on how many pixels we get per update (PIXELS / UPDATE)
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private final Joystick joystick;
    private double radius;
    private Paint paint;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.joystick = joystick;
        this.radius = radius;
        paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.player);
        paint.setColor(colour);

    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float)positionX, (float)positionY, (float)radius, paint);
    }

    public void update() {
        //update velocity based on actuator of Joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        //update position
        positionX += velocityX;
        positionY += velocityY;
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
