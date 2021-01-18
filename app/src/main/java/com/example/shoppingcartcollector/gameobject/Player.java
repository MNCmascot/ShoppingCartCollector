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

    //data for drawing trailing carts
    private float cartWidth;
    private float cartHeight;
    private double movementAngle;
    private Paint cartPaint;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.joystick = joystick;
        this.radius = radius;
        this.cartsCollected = 0;
        this.dead = false;

        //set up extra info for drawing trailing carts
        this.cartWidth = 60; //manually set
        this.cartHeight = 40; //manually set
        this.movementAngle = 0; //initialize as 0

        //set up player colour
        paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.player);
        paint.setColor(colour);

        //set up Cart colour
        cartPaint = new Paint();
        int colour2 = ContextCompat.getColor(context, R.color.cart);
        cartPaint.setColor(colour2);

    }

    public void incrementCartsCollected() {
        cartsCollected += 1;
        Log.d("DEBUG", "Carts Collected: " + cartsCollected);

    }

    public void draw(Canvas canvas) {
        //draw player
        canvas.drawCircle((float)positionX, (float)positionY, (float)radius, paint);

        //draw trailing carts
        canvas.save(); //save canvas state before rotating

        //rotate canvas in movement direction (to draw carts trailing behind
        canvas.rotate((float)movementAngle, (float)positionX, (float)positionY);
        for (int i = 1; i <= cartsCollected; i++) {
            canvas.drawRect((float) (positionX - 60*i - radius), (float) (positionY - 20),
                    (float) (positionX - 60*(i-1) - radius), (float) (positionY + 20), cartPaint);
        }
        canvas.restore(); //restore rotation
    }

    public void update() {
        //update velocity based on actuator of Joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        //get direction that player is moving in and turn to degrees
        double tempAngle = Math.toDegrees(Math.atan2(joystick.getActuatorY(), joystick.getActuatorX()));
        //only update if player is moving so it doesn't snap back behind them
        if (tempAngle != 0) {
            movementAngle = Math.toDegrees(Math.atan2(joystick.getActuatorY(), joystick.getActuatorX()));
        }
        //update position
        positionX += velocityX;
        positionY += velocityY;

        //update direction
        if (velocityX != 0 || velocityY != 0) {
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
        }
    }

    public double getRadius() { return radius; }
    public int getCartsCollected() { return cartsCollected; }
    public void setCartsCollected(int newCartsCollected) {cartsCollected = newCartsCollected; }
    public boolean isDead() { return dead; }
    public void setDead (boolean dead) { this.dead = dead; }

}
