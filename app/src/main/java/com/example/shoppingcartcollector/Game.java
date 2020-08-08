package com.example.shoppingcartcollector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.core.content.ContextCompat;

/*
The Game class manages all objects in the game, as well as updating states and rendering objects to the screen


 */


public class Game extends SurfaceView implements SurfaceHolder.Callback{
    private final Player player;
    private final Joystick joystick;
    private final Cart cart;
    private GameLoop gameLoop;

    public Game(Context context) {
        super(context);

        //get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        //Create GameLoop class
        gameLoop = new GameLoop(this, surfaceHolder);
        setFocusable(true);


        //initialize Joystick
        joystick = new Joystick(275, 700, 70, 40);

        //Initialize player
        player = new Player(getContext(), joystick,1000, 500, 30);

        //Initialize shopping cart object

        cart = new Cart();
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

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Display UPS
        drawUPS(canvas);
        //Display FPS
        drawFPS(canvas);
        //display player
        player.draw(canvas);
        //dispay joystick
        joystick.draw(canvas);

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
    }
}
