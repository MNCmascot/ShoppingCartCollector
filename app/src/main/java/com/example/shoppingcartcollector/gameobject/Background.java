package com.example.shoppingcartcollector.gameobject;

/*
Background images used during gameplay
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.shoppingcartcollector.R;


public class Background extends GameObject{
    private int width = 0, height = 0;
    private Drawable myImage;

    public Background(Context context, double positionX, double positionY, int width, int height) {
        super(positionX, positionY);
        this.width = width;
        this.height = height;

        this.myImage = ResourcesCompat.getDrawable(context.getResources(), R.drawable.background2, null);
    }

    @Override
    public void draw(Canvas canvas) {
        //canvas.set
        Rect imageBounds = canvas.getClipBounds();
        imageBounds.left = (int)positionX;
        imageBounds.top = (int)positionY;
        imageBounds.right = (int)(positionX+width);
        imageBounds.bottom = (int)(positionY + height);
        myImage.setBounds(imageBounds);
        myImage.draw(canvas);

    }

    @Override
    public void update() {

    }


    public int getWidth() { return width; }
    public int getHeight() { return height; }

}

