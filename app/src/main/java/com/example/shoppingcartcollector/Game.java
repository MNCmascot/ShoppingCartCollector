package com.example.shoppingcartcollector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

import com.example.shoppingcartcollector.gameobject.Cart;
import com.example.shoppingcartcollector.gameobject.CartZone;
import com.example.shoppingcartcollector.gameobject.GameObject;
import com.example.shoppingcartcollector.gameobject.Player;
import com.example.shoppingcartcollector.gameobject.Wall;
import com.example.shoppingcartcollector.gamepanel.GameOver;
import com.example.shoppingcartcollector.gamepanel.Joystick;
import com.example.shoppingcartcollector.gamepanel.Performance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
The Game class manages all objects in the game, as well as updating states and rendering objects to the screen

 */


public class Game extends SurfaceView implements SurfaceHolder.Callback{
    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;
    private List<Cart> cartList = new ArrayList<Cart>();
    private List<Wall> wallList = new ArrayList<Wall>();
    private CartZone cartZone;
    private int joystickPointerId = 0;
    private GameOver gameOver;
    private Performance performance;

    public Game(Context context) {
        super(context);

        //get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        //Create GameLoop class
        gameLoop = new GameLoop(this, surfaceHolder);

        //Initialize game over panel
        gameOver = new GameOver(context);

        //initialize Joystick panel
        joystick = new Joystick(275, 700, 70, 40);

        //initialize performance panel (for drawing UPS and FPS)
        performance = new Performance(context, gameLoop);

        //Initialize player
        player = new Player(context, joystick,1000, 500, 30);

        //Initialize walls
        Wall wall1 = new Wall(context, 300, 300, 500, 100);
        wallList.add(wall1);

        //Initialize Cart Zone (to bring the carts to)
        cartZone = new CartZone(context, 1000, 100, 1000, 150, 50);

        setFocusable(true);
    }


    //handle touch event actions
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //joystick was pressed
                if (joystick.isPressed((double)event.getX(), (double)event.getY())){
                    //store ID (to handle multi-touches)
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    // set it as pressed
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
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId(event.getActionIndex())){
                    //let go of joystick
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }

                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");

        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("MainActivity.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("MainActivity.java", "surfaceDestroyed()");
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
        //display walls
        for (Wall wall: wallList) {
            wall.draw(canvas);
        }
        //display CartZone
        cartZone.draw(canvas);

        //display UPS and FPS
        performance.draw(canvas);



        //Draw Game Over if the player is dead
        if (player.isDead()) {
            gameOver.draw(canvas);
        }
    }


    //update game state
    public void update() {

        //stop updating the game if the player is dead
        if (player.isDead()){
            return;
        }

        player.update();
        joystick.update();
        cartZone.update();

        /*
        //spawn a new cart if it is time to spawn one
        //make sure it is not colliding with the joystick outer circle or a Wall
        */
        if (Cart.readyToSpawn()) {
            //decrease number of carts in store by 1
            cartZone.setCartsLeft(cartZone.getCartsLeft()-1);
            Log.d("DEBUG", cartZone.getCartsLeft() + " carts left.");
            //If no carts left, game over
            if (cartZone.getCartsLeft() <= 0) {
                player.setDead(true);
            }

            Cart tempCart;
            double distance; //distance between joystick and new cart
            double distanceToCollision; //distance between radii
            boolean underWall; //temporary boolean if under a wall
            do { //get another position if new cart is under joystick or under wall
                double tempX = Math.random() * 2000 + 50; //random X location
                double tempY = Math.random() * 750 + 300; //random Y location
                underWall = false; //Initialize as false, not under a wall

                cartList.add(new Cart(getContext(), player, tempX, tempY, 20));
                tempCart = cartList.get(cartList.size() - 1); //last cart added

                //distance between new cart and joystick
                distance = Utils.getDistanceBetweenPoints(tempCart.getPositionX(), tempCart.getPositionY(),
                        joystick.getOuterCircleCenterPositionX(), joystick.getOuterCircleCenterPositionY());
                //distance between radii
                distanceToCollision = tempCart.getRadius() + joystick.getOuterCircleRadius();

                //Iterate through wallList to check if cart is under any of the walls
                Iterator<Wall> wallIterator = wallList.iterator();
                while (wallIterator.hasNext()) {
                    Wall wallCheck = wallIterator.next();
                    if (Utils.circleRectangleCollision(tempCart.getPositionX(), tempCart.getPositionY(), tempCart.getRadius(),
                            wallCheck.getPositionX(), wallCheck.getPositionY(), wallCheck.getWidth(), wallCheck.getHeight())){
                        underWall = true;
                        break;
                    }
                }

                if (distance < distanceToCollision || underWall) { //under joystick or under wall, remove cart
                    cartList.remove(cartList.size()-1);
                    //Log.d("DEBUG", "Cart under joystick");
                }

            } while ((distance < distanceToCollision) || underWall);
        }

        //update each Cart
        for (Cart cart : cartList) {
            cart.update();
        }

        //update each Wall
        for (Wall wall : wallList) {
            wall.update();
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

        //Iterate through wallList to check for collisions with the player
        Iterator<Wall> wallIterator = wallList.iterator();
        while (wallIterator.hasNext()) {
            Wall wallCheck = wallIterator.next();

            if(Utils.circleRectangleCollision(player.getPositionX(), player.getPositionY(), player.getRadius(),
                    wallCheck.getPositionX(), wallCheck.getPositionY(), wallCheck.getWidth(), wallCheck.getHeight())){
               // Log.d("DEBUG", "player colliding with wall!");
                player.setDead(true);
            }
        }

        //Check if player has carts and "collided" with the Cart Zone and increase number of carts left if so.
        if( player.getCartsCollected() >= 0 &&
            Utils.circleRectangleCollision(player.getPositionX(), player.getPositionY(), player.getRadius(),
                cartZone.getPositionX(), cartZone.getPositionY(), cartZone.getWidth(), cartZone.getHeight())){
            //Increase the carts left by the amount the player has
            cartZone.setCartsLeft(cartZone.getCartsLeft() + player.getCartsCollected());
            //Set player's collected amount to 0 (no carts collected anymore)
            player.setCartsCollected(0);
        }

    }

    //Pause the game when user exits
    public void pause() {
        gameLoop.stopLoop();
    }
}
