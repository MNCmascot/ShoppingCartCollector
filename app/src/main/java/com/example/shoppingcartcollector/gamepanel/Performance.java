package com.example.shoppingcartcollector.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;

import java.util.Timer;

import androidx.core.content.ContextCompat;

import com.example.shoppingcartcollector.GameLoop;
import com.example.shoppingcartcollector.R;

public class Performance {

    private GameLoop gameLoop;
    private Context context;
    private int seconds, minutes;
    private long startTime, deathTime;
    private boolean gameOver = false;

    public Performance(Context context, GameLoop gameLoop) {
        this.context = context;
        this.gameLoop = gameLoop;
        //initialize time to current time
        this.startTime = System.currentTimeMillis();
        this.seconds = 0;
        this.minutes = 0;
        this.deathTime = 0;
    }

    public void draw(Canvas canvas) {
        //Draw Updates Per Second
        //drawUPS(canvas);
        //Draw Frames Per Second
        drawFPS(canvas);
        //Draw time alive (iF game isn't over)
        drawTimer(canvas);
    }

    public void setGameOver(){
        gameOver = true;
        this.deathTime = System.currentTimeMillis();
    }

    //get total seconds spent
    public int getSeconds() { return seconds; }

    //display updates per second
    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.cyan);
        paint.setColor(colour);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 50, 100, paint);
    }

    //display frames per second
    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.cyan);
        paint.setColor(colour);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 50, 100, paint);
    }

    //display time alive
    public void drawTimer(Canvas canvas){
        //calculate time alive
        long millis;
        if (!gameOver) { //Game not over, keep counting
            millis = System.currentTimeMillis() - startTime;
        } else { //Game over, stop timer
            millis = deathTime - startTime;
        }
        seconds = (int) (millis/1000);
        minutes = seconds / 60;

        //seconds calculation done here so we still have total seconds spent
        String timer = String.format("%d:%02d", minutes, seconds % 60);

        Paint paint = new Paint();
        //uses same colour as number of carts left text
        int colour = ContextCompat.getColor(context, R.color.cartsleft);
        paint.setColor(colour);
        paint.setTextSize(50);
        canvas.drawText("Time: " + timer, 50, 200, paint);
    }
}
