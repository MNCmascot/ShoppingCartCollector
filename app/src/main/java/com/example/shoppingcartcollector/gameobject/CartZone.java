package com.example.shoppingcartcollector.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.shoppingcartcollector.R;

public class CartZone extends GameObject {

    //Number of carts in total
    protected int cartsLeft;
    private int width = 0, height = 0;
    private Paint paint;
    private Paint cartsTextPaint;

    public CartZone(Context context, double positionX, double positionY, int width, int height, int cartsLeft) {
        super(positionX, positionY);
        this.cartsLeft = cartsLeft;
        this.width = width;
        this.height = height;

        //set up wall colour
        paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.cartzone);
        paint.setColor(colour);

        //set up cart text colour
        cartsTextPaint = new Paint();
        int colour2 = ContextCompat.getColor(context, R.color.cartsleft);
        cartsTextPaint.setColor(colour2);
        cartsTextPaint.setTextSize(50);

    }

    @Override
    public void draw(Canvas canvas) {
        //Draw how many carts are left in the store
        canvas.drawText("Carts left in store: " + cartsLeft, 300, 100, cartsTextPaint);
        //Draw the cartZone
        canvas.drawRect((float)positionX, (float)positionY,
                (float)(positionX+width), (float)(positionY + height), paint);
    }

    @Override
    public void update() {

    }

    public void setCartsLeft(int newCartsLeft) {cartsLeft = newCartsLeft;}
    public int getCartsLeft() { return cartsLeft; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
