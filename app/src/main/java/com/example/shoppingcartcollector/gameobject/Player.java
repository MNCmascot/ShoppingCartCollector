package com.example.shoppingcartcollector.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.shoppingcartcollector.GameLoop;
import com.example.shoppingcartcollector.gamepanel.Joystick;
import com.example.shoppingcartcollector.R;
import com.example.shoppingcartcollector.Utils;

/*
   Main character of the game, controlled by the joystick.
   extension of GameObject
 */

public class Player extends GameObject{
    private static final double SPEED_PIXELS_PER_SECOND = 400.0; //actual speed value
    //determine max speed based on how many pixels we get per update (PIXELS / UPDATE)
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    protected int cartsCollected = 0;
    private final Joystick joystick;
    private double radius;
    private Paint paint;
    private boolean dead;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.joystick = joystick;
        this.radius = radius;
        this.cartsCollected = 0;
        this.dead = false;

        //set up player colour
        paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.player);
        paint.setColor(colour);

    }

    public void incrementCartsCollected() {
        cartsCollected += 1;
        Log.d("DEBUG", "Carts Collected: " + cartsCollected);

    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float)positionX, (float)positionY, (float)radius, paint);
    }

    public double getRadius() { return radius; }

    public void update() {
        //update velocity based on actuator of Joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        //update position
        positionX += velocityX;
        positionY += velocityY;

        //update direction
        if (velocityX != 0 || velocityY != 0) {
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
        }
    }

    public boolean isDead() { return dead; }
    public void setDead (boolean dead) { this.dead = dead; }

}
