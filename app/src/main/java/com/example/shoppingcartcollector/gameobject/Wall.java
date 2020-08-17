package com.example.shoppingcartcollector.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.shoppingcartcollector.R;


/*
Walls (or curbs) that the player may collide with
* */

public class Wall extends GameObject{

    private int width = 0, height = 0;
    private Paint paint;
    public Wall(Context context, double positionX, double positionY, int width, int height) {
        super(positionX, positionY);
        this.width = width;
        this.height = height;

        //set up wall colour
        paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.wall);
        paint.setColor(colour);

    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect((float)positionX, (float)positionY,
                (float)(positionX+width), (float)(positionY + height), paint);

    }

    @Override
    public void update() {
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
