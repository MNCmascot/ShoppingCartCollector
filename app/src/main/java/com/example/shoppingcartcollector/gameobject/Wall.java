package com.example.shoppingcartcollector.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.shoppingcartcollector.R;


/*
Walls (or curbs) that the player may collide with
//Wall image from here https://opengameart.org/content/wall-of-large-bricks
* */

public class Wall extends GameObject{

    private int width = 0, height = 0;
    //private Paint paint;
    private Drawable myImage;
    public Wall(Context context, double positionX, double positionY, int width, int height) {
        super(positionX, positionY);
        this.width = width;
        this.height = height;

        //set image
        this.myImage = ResourcesCompat.getDrawable(context.getResources(), R.drawable.wall, null);

        //set up wall colour
        //paint = new Paint();
        //int colour = ContextCompat.getColor(context, R.color.wall);
        //paint.setColor(colour);

    }


    @Override
    public void draw(Canvas canvas) {
        Rect imageBounds = canvas.getClipBounds();
        imageBounds.left = (int)positionX;
        imageBounds.top = (int)positionY;
        imageBounds.right = (int)(positionX+width);
        imageBounds.bottom = (int)(positionY + height);
        myImage.setBounds(imageBounds);
        myImage.draw(canvas);

        //canvas.drawRect((float)positionX, (float)positionY,
        //        (float)(positionX+width), (float)(positionY + height), paint);

    }

    @Override
    public void update() {
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
