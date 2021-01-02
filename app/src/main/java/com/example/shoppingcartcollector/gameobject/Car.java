package com.example.shoppingcartcollector.gameobject;

/*
The Car class represents the cars that move across the screen, which the player may collide with.
Drawn as rectangles.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.shoppingcartcollector.R;

public class Car extends GameObject{
    private int width = 0, height = 0;
    private Paint paint;

    public Car(Context context, double positionX, double positionY, int width, int height) {
        super(positionX, positionY);
        this.width = width;
        this.height = height;

        //set up car colour
        paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.car);
        paint.setColor(colour);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect((float)positionX, (float)positionY,
                (float)(positionX+width), (float)(positionY + height), paint);
    }

    @Override
    public void update() {
        //Move car to the right (deletion handled in Game.java)
        positionX += 7;

    }


    public int getWidth() { return width; }
    public int getHeight() { return height; }

}
