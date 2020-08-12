package com.example.shoppingcartcollector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

import com.example.shoppingcartcollector.object.Cart;
import com.example.shoppingcartcollector.object.GameObject;
import com.example.shoppingcartcollector.object.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
The Game class manages all objects in the game, as well as updating states and rendering objects to the screen


 */


public class Game extends SurfaceView implements SurfaceHolder.Callback{
    private final Player player;
    private final Joystick joystick;
    //private final Cart cart;
    private GameLoop gameLoop;
    private List<Cart> cartList = new ArrayList<Cart>();

    public Game(Context context) {
        super(context);

        //get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        //Create GameLoop class
        gameLoop = new GameLoop(this, surfaceHolder);


        //initialize Joystick
        joystick = new Joystick(275, 700, 70, 40);

        //Initialize player
        player = new Player(getContext(), joystick,1000, 500, 30);


        setFocusable(true);
    }


    //handle touch event actions
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //joystick was pressed
                if (joystick.isPressed((double)event.getX(), (double)event.getY())){
                    joystick.setIsPressed(true);
                }
               // player.setPosition((double)event.getX(), (double)event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                //if joystick is pressed:
                if (joystick.getIsPressed()) {
                    joystick.setActuator((double)event.getX(), (double)event.getY());
                }
              //  player.setPosition((double)event.getX(), (double)event.getY());
                return true;
            case MotionEvent.ACTION_UP:
                //let go of joystick
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameLoop.startLoop();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /*
    Draw to canvas (displayed in the order from bottom to top. ex: player should not be displayed
    over the joystick)
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //display joystick
        joystick.draw(canvas);
        //display carts
        for (Cart cart: cartList) {
            cart.draw(canvas);
        }
        //display player
        player.draw(canvas);

        //Display UPS
        drawUPS(canvas);
        //Display FPS
        drawFPS(canvas);
    }

    //display updates per second
    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int colour = ContextCompat.getColor(getContext(), R.color.cyan);
        paint.setColor(colour);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 100, paint);
    }

    //display frames per second
    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int colour = ContextCompat.getColor(getContext(), R.color.cyan);
        paint.setColor(colour);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);
    }

    //update game state
    public void update() {
        player.update();
        joystick.update();

        /*
        //spawn a new cart if it is time to spawn one
        //make sure it is not colliding with the joystick outer circle
        */
        if (Cart.readyToSpawn()) {
            Cart tempCart;
            double distance; //distance between joystick and new cart
            double distanceToCollision; //distance between radii
            do { //get another position if new cart is under joystick
                double tempX = Math.random() * 2000 + 50; //random X location
                double tempY = Math.random() * 750 + 300; //random Y location

                cartList.add(new Cart(getContext(), player, tempX, tempY, 20));
                tempCart = cartList.get(cartList.size() - 1); //last cart added

                //distance between new cart and joystick
                distance = Math.sqrt(
                        Math.pow(tempCart.getPositionX() - joystick.getOuterCircleCenterPositionX(), 2) +
                        Math.pow(tempCart.getPositionY() - joystick.getOuterCircleCenterPositionY(), 2)
                );
                //distance between radii
                distanceToCollision = tempCart.getRadius() + joystick.getOuterCircleRadius();

                if (distance < distanceToCollision) { //under joystick, remove cart
                    cartList.remove(cartList.size()-1);
                    //Log.d("DEBUG", "Cart under joystick");
                }

            } while (distance < distanceToCollision);
        }

        //update each Cart
        for (Cart cart : cartList) {
            cart.update();
        }

        //iterate through cartList to check for collisions with the player
        Iterator<Cart> cartIterator = cartList.iterator();
        while (cartIterator.hasNext()) {
            Cart cartCheck = cartIterator.next();
            //player has collided with cart (pick it up)
            if (GameObject.areCirclesColliding(cartCheck, cartCheck.getRadius(),
                    player, player.getRadius())) {
                //remove cart if it collides with the player
                cartIterator.remove();
                //increase number of carts collected
                player.incrementCartsCollected();
            }
        }

    }
}
