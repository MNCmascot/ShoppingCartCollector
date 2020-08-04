package com.example.shoppingcartcollector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.core.content.ContextCompat;

/*
The Game class manages all objects in the game, as well as updating states and rendering objects to the screen


 */


public class Game extends SurfaceView implements SurfaceHolder.Callback{
    private GameLoop gameLoop;
    private Context context;

    public Game(Context context) {
        super(context);

        //get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.context = context;
        //Create GameLoop class
        gameLoop = new GameLoop(this, surfaceHolder);

        setFocusable(true);
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
    }

    //display updates per second
    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.cyan);
        paint.setColor(colour);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 100, paint);
    }

    //display frames per second
    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.cyan);
        paint.setColor(colour);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 200, paint);
    }

    public void update() {
    }
}
