package com.example.shoppingcartcollector.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.shoppingcartcollector.GameLoop;
import com.example.shoppingcartcollector.gamepanel.Joystick;
import com.example.shoppingcartcollector.R;
import com.example.shoppingcartcollector.Utils;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/*
   Main character of the game, controlled by the joystick.
   extension of GameObject

   player image: https://kenney.nl/assets/topdown-shooter
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
    private Drawable playerImage;

    //data for drawing trailing carts
    private float cartWidth;
    private float cartHeight;
    private double joystickMovementAngle;
    //private Paint cartPaint;
    private Drawable cartImage;

    private double moveAngle;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.joystick = joystick;
        this.radius = radius;
        this.cartsCollected = 0;
        this.dead = false;

        //set image
        this.playerImage = ResourcesCompat.getDrawable(context.getResources(), R.drawable.player, null);


        //set up extra info for drawing trailing carts
        this.cartWidth = 60; //manually set
        this.cartHeight = 40; //manually set
        this.joystickMovementAngle = 0; //initialize as 0
        //set cart image
        this.cartImage = ResourcesCompat.getDrawable(context.getResources(), R.drawable.cart, null);

        //used for player movement (different when carts are collected)
        this.moveAngle = 0; // initialize to 0

        //set up player colour
        paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.player);
        paint.setColor(colour);

        //set up Cart colour
       // cartPaint = new Paint();
       // int colour2 = ContextCompat.getColor(context, R.color.cart);
       // cartPaint.setColor(colour2);

    }

    public void incrementCartsCollected() {
        cartsCollected += 1;
        Log.d("DEBUG", "Carts Collected: " + cartsCollected);


    }

    public void draw(Canvas canvas) {

        //draw player
       // canvas.drawCircle((float)positionX, (float)positionY, (float)radius, paint);

        //draw trailing carts
        canvas.save(); //save canvas state before rotating

        //rotate canvas in movement direction (to draw carts trailing behind)
        canvas.rotate((float) moveAngle, (float) positionX, (float) positionY);


        Rect playerBounds = canvas.getClipBounds();
        playerBounds.left = (int)(positionX-radius);
        playerBounds.top = (int)(positionY-radius);
        playerBounds.right = (int)(positionX+radius);
        playerBounds.bottom = (int)(positionY+radius);
        playerImage.setBounds(playerBounds);
        playerImage.draw(canvas);

        for (int i = 1; i <= cartsCollected; i++) {
            Rect imageBounds = canvas.getClipBounds();
            imageBounds.left = (int)(positionX + 60*(i-1) + radius);
            imageBounds.top = (int)(positionY - 20);
            imageBounds.right = (int)(positionX + 60*i+ radius);
            imageBounds.bottom = (int)(float) (positionY + 20);
            cartImage.setBounds(imageBounds);
            cartImage.draw(canvas);

            //canvas.drawRect((float) (positionX - 60*i - radius), (float) (positionY - 20),
             //       (float) (positionX - 60*(i-1) - radius), (float) (positionY + 20), cartPaint);
        }
        canvas.restore(); //restore rotation
    }

    public void update() {

        //old movement
        //velocityX = joystick.getActuatorX()*MAX_SPEED;
        //velocityY = joystick.getActuatorY()*MAX_SPEED;

        //get direction that player is moving in and turn to degrees
        double tempAngle = Math.toDegrees(Math.atan2(joystick.getActuatorY(), joystick.getActuatorX()));
        //only update if player is moving so it doesn't snap back behind them
        if (tempAngle != 0) {
            joystickMovementAngle = tempAngle;
        }

        if (cartsCollected == 0) { //change position normally if no carts collected
            //update velocity based on actuator of Joystick
            moveAngle = joystickMovementAngle;
            velocityX = cos(Math.toRadians(moveAngle))*MAX_SPEED;
            velocityY = sin(Math.toRadians(moveAngle))*MAX_SPEED;

        } else if (tempAngle != 0 && //not stationary
                joystickMovementAngle > -90 && joystickMovementAngle < 90){ //moving RIGHT
            if (cartsCollected >= 20){ //max turn reduction for 20 carts
                moveAngle += 1;
            } else { //the more carts you have, the slower you turn
                moveAngle += (3 - 0.1 * cartsCollected);
            }
        } else if (tempAngle != 0) {//moving LEFT when joystick angle < -90 or > 90
            if (cartsCollected >= 20){ //max turn reduction for 20 carts
                moveAngle -= 1;
            } else { //the more carts you have, the slower you turn
                moveAngle -= (3 - 0.1 * cartsCollected);
            }
        }

        if (tempAngle != 0) { //update velocity if
            velocityX = cos(Math.toRadians(moveAngle)) * MAX_SPEED;
            velocityY = sin(Math.toRadians(moveAngle)) * MAX_SPEED;
        } else { //player not moving
            velocityX = 0;
            velocityY = 0;
        }
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
