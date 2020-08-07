package com.example.shoppingcartcollector;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread{
    public static final double MAX_UPS = 60.0; //Max updates per second (target)
    private static final double UPS_PERIOD = 1000/MAX_UPS; //converted for convenience in other time calculations

    //is the game running?
    private boolean isRunning = false;

    private Game game; //get the game class
    private SurfaceHolder surfaceHolder; //get the surfaceHolder
    private double averageUPS;
    private double averageFPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public double getAverageUPS() {
        return averageUPS;
    }


    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        isRunning = true;
        start(); //Start a thread with thread class
    }

    @Override
    public void run() {
        super.run();

        //time and cycle count variables for displaying UPS and FPS
        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        //Game Loop

        Canvas canvas = null; //create canvas for display

        startTime = System.currentTimeMillis(); //start time before loop

        while(isRunning) {

            //Update and render game
            //Make sure canvas isn't already locked with try statement
            try {
                canvas = surfaceHolder.lockCanvas(); //lock canvas
                synchronized (surfaceHolder) { //prohibit multiple threads from updating and drawing
                    game.update();
                    updateCount++; //update counter after updating game

                    game.draw(canvas);
                }


            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try { //surround unlock of canvas in it's own try and catch in case we want to handle this exception differently
                        surfaceHolder.unlockCanvasAndPost(canvas); //unlock canvas after use
                        frameCount++; //update counter after drawing to game
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }



            //Pause game loop (don't exceed target UPS)
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            if(sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //Skip frames to keep up with target UPS
            while(sleepTime < 0 && updateCount < MAX_UPS-1) { //make sure it doesn't go over 60 UPS
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }

            //Calculate average UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000) { //1 second has passed
                averageUPS = (double)updateCount / (double)(elapsedTime/1000); //make conversion into seconds instead of ms
                averageFPS = (double)frameCount / (double)(elapsedTime/1000);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }

        }
    }
}

